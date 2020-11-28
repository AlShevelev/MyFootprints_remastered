package com.shevelev.my_footprints_remastered.sync.gd_operations

import com.google.api.client.http.ByteArrayContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import com.shevelev.my_footprints_remastered.sync.footprint_meta_gd_crypt.FootprintMetaGoogleDrive
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveCredentials
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.lang.UnsupportedOperationException
import javax.inject.Inject

class GoogleDriveOperationsImpl
@Inject
constructor(
    private val appName: String,
    private val gdCredentials: GoogleDriveCredentials
) : GoogleDriveOperations {

    private companion object {
        private const val SPACE = "appDataFolder"
        private const val MIME_TYPE = "application/octet-stream"

        private const val COMMENT = "COM"
        private const val COMMENT_1 = "COM1"
        private const val COMMENT_2 = "COM2"
        private const val COMMENT_3 = "COM"
        private const val ALL_THE_REST = "REST"
    }

    private val service: Drive? by lazy { createService() }

    override fun create(footprintMetadata: FootprintMetaGoogleDrive, content: ByteArray, fileName: String): GoogleDriveFileId =
        processDriveOperation { service ->
            val fileMetadata = File()

            fileMetadata.name = fileName
            fileMetadata.parents = listOf(SPACE)
            saveProperties(fileMetadata, footprintMetadata)

            val fileContent = ByteArrayContent(MIME_TYPE, content)

            val result = service.files().create(fileMetadata, fileContent).execute()
            GoogleDriveFileId(result.id)
        }

    override fun read(fileId: GoogleDriveFileId): GoogleDriveFile =
        processDriveOperation { service ->
            val fileRequest = service
                .files()
                .get(fileId.id)
                .setFields("name, appProperties")

            val fileMetadata = fileRequest.execute()
            val metadata = loadProperties(fileMetadata)

            ByteArrayOutputStream().use { outputStream ->
                fileRequest.executeMediaAndDownloadTo(outputStream)

                val content = outputStream.toByteArray()
                GoogleDriveFile(fileId, fileMetadata.name, metadata, content)
            }
        }

    override fun readFilesList(): List<GoogleDriveFileId> =
        processDriveOperation { service ->
            var  pageToken: String? = null

            val result = mutableListOf<GoogleDriveFileId>()

            do {
                val filesList = service
                    .files()
                    .list()
                    .setSpaces(SPACE)
                    .setFields("nextPageToken, files(id)")
                    .setPageToken(pageToken)
                    .execute()

                filesList.files.forEach { file ->
                    result.add(GoogleDriveFileId(file.id))
                }

                pageToken = filesList.nextPageToken
            } while (pageToken != null)

            result
        }

    override fun updateMetadata(footprintMetadata: FootprintMetaGoogleDrive, fileId: GoogleDriveFileId): Unit =
        processDriveOperation { service ->
            val oldFileMetadata = service
                .files()
                .get(fileId.id)
                .setFields("name")
                .execute()

            val newFileMetadata = File()
            newFileMetadata.name = oldFileMetadata.name
            saveProperties(newFileMetadata, footprintMetadata)

            service.files().update(fileId.id, newFileMetadata).execute()
        }

    override fun updateContent(content: ByteArray, fileId: GoogleDriveFileId): Unit =
        processDriveOperation { service ->
            val oldFileMetadata = service
                .files()
                .get(fileId.id)
                .setFields("name, appProperties")
                .execute()

            val newFileMetadata = File()
            newFileMetadata.name = oldFileMetadata.name
            newFileMetadata.appProperties = oldFileMetadata.appProperties

            val fileContent = ByteArrayContent(MIME_TYPE, content)

            service.files().update(fileId.id, newFileMetadata, fileContent).execute()
        }

    override fun updateAll(footprintMetadata: FootprintMetaGoogleDrive, content: ByteArray, fileId: GoogleDriveFileId): Unit =
        processDriveOperation { service ->
            val oldFileMetadata = service
                .files()
                .get(fileId.id)
                .setFields("name")
                .execute()

            val newFileMetadata = File()
            newFileMetadata.name = oldFileMetadata.name
            saveProperties(newFileMetadata, footprintMetadata)

            val fileContent = ByteArrayContent(MIME_TYPE, content)

            service.files().update(fileId.id, newFileMetadata, fileContent).execute()
        }

    override fun delete(fileId: GoogleDriveFileId): Unit =
        processDriveOperation { service ->
            service.files().delete(fileId.id).execute()
        }

    private fun createService(): Drive? =
        gdCredentials.getCredentials()?.let { credentials ->
            Drive.Builder(NetHttpTransport(), GsonFactory(), credentials)
                .setApplicationName(appName)
                .build()
        }

    private fun <TR>processDriveOperation(action: (service: Drive) -> TR): TR {
        if(service == null) {
            throw IllegalStateException("Drive service is not ready")
        }

        return try {
            action(service!!)
        } catch (ex: Exception) {
            Timber.e(ex)
            throw ex
        }
    }

    private fun saveProperties(file: File, metadata: FootprintMetaGoogleDrive) {
        file.appProperties = mutableMapOf<String, String>()
            .apply {
                put(ALL_THE_REST, metadata.mainData)

                metadata.comment.forEachIndexed { index, line ->
                    val key = when(index) {
                        0 -> COMMENT
                        1 -> COMMENT_1
                        2 -> COMMENT_2
                        3 -> COMMENT_3
                        else -> throw UnsupportedOperationException("Comment is too long")
                    }
                    put(key, line)
                }
            }
    }

    private fun loadProperties(file: File): FootprintMetaGoogleDrive {
        return FootprintMetaGoogleDrive(
            mainData = file.appProperties[ALL_THE_REST]!!,
            comment = listOfNotNull(
                file.appProperties[COMMENT],
                file.appProperties[COMMENT_1],
                file.appProperties[COMMENT_2],
                file.appProperties[COMMENT_3]
            )
        )
    }
}