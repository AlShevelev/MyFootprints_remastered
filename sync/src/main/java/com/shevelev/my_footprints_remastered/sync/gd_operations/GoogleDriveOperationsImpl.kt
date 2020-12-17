package com.shevelev.my_footprints_remastered.sync.gd_operations

import com.google.api.client.http.ByteArrayContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import com.shevelev.my_footprints_remastered.common_entities.sync.GoogleDriveFileId
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
        private const val SPACE = "drive"

        private const val FILE_MIME_TYPE = "application/octet-stream"
        private const val FOLDER_MIME_TYPE = "application/vnd.google-apps.folder"

        private const val COMMENT = "COM"
        private const val COMMENT_1 = "COM1"
        private const val COMMENT_2 = "COM2"
        private const val COMMENT_3 = "COM"
        private const val ALL_THE_REST = "REST"
    }

    private val folderName = appName

    private val service: GoogleDriveService? by lazy { createService() }

    override fun create(footprintMetadata: FootprintMetaGoogleDrive, content: ByteArray, fileName: String): GoogleDriveFileId =
        processDriveOperation { drive, folderId ->
            val fileMetadata = File()

            fileMetadata.name = fileName
            fileMetadata.parents = listOf(folderId.id)
            saveProperties(fileMetadata, footprintMetadata)

            val fileContent = ByteArrayContent(FILE_MIME_TYPE, content)

            val result = drive.files().create(fileMetadata, fileContent).execute()
            GoogleDriveFileId(result.id)
        }

    override fun read(fileId: GoogleDriveFileId): GoogleDriveFile =
        processDriveOperation { drive, _ ->
            val fileRequest = drive
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
        processDriveOperation { drive, folderId ->
            var  pageToken: String? = null

            val result = mutableListOf<GoogleDriveFileId>()

            do {
                val filesList = drive
                    .files()
                    .list()
                    .setQ("'${folderId.id}' in parents")
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
        processDriveOperation { drive, _ ->
            val oldFileMetadata = drive
                .files()
                .get(fileId.id)
                .setFields("name")
                .execute()

            val newFileMetadata = File()
            newFileMetadata.name = oldFileMetadata.name
            saveProperties(newFileMetadata, footprintMetadata)

            drive.files().update(fileId.id, newFileMetadata).execute()
        }

    override fun updateContent(content: ByteArray, fileId: GoogleDriveFileId): Unit =
        processDriveOperation { drive, _ ->
            val oldFileMetadata = drive
                .files()
                .get(fileId.id)
                .setFields("name, appProperties")
                .execute()

            val newFileMetadata = File()
            newFileMetadata.name = oldFileMetadata.name
            newFileMetadata.appProperties = oldFileMetadata.appProperties

            val fileContent = ByteArrayContent(FILE_MIME_TYPE, content)

            drive.files().update(fileId.id, newFileMetadata, fileContent).execute()
        }

    override fun updateAll(footprintMetadata: FootprintMetaGoogleDrive, content: ByteArray, fileId: GoogleDriveFileId): Unit =
        processDriveOperation { drive, _ ->
            val oldFileMetadata = drive
                .files()
                .get(fileId.id)
                .setFields("name")
                .execute()

            val newFileMetadata = File()
            newFileMetadata.name = oldFileMetadata.name
            saveProperties(newFileMetadata, footprintMetadata)

            val fileContent = ByteArrayContent(FILE_MIME_TYPE, content)

            drive.files().update(fileId.id, newFileMetadata, fileContent).execute()
        }

    override fun delete(fileId: GoogleDriveFileId): Unit =
        processDriveOperation { drive, _ ->
            drive.files().delete(fileId.id).execute()
        }

    private fun createService(): GoogleDriveService? =
        gdCredentials.getCredentials()?.let { credentials ->
            Drive.Builder(NetHttpTransport(), GsonFactory(), credentials)
                .setApplicationName(appName)
                .build()
        }
            ?.let { service ->
                val folderId = getFolderByName(folderName, service) ?: createFolder(folderName, service)
                GoogleDriveService(service, folderId)
            }

    private fun getFolderByName(folderName: String, service: Drive): GoogleDriveFileId? =
        service
            .files()
            .list()
            .setQ("mimeType='$FOLDER_MIME_TYPE' and name='$folderName'")
            .setSpaces(SPACE)
            .setFields("files(id, name)")
            .execute()
            .let {
                it.files.firstOrNull()?.let { file -> GoogleDriveFileId(file.id) }
            }

    private fun createFolder(folderName: String, service: Drive): GoogleDriveFileId {
        val fileMetadata = File()

        fileMetadata.name = folderName
        fileMetadata.mimeType = FOLDER_MIME_TYPE

        val result = service
            .files()
            .create(fileMetadata)
            .setFields("id")
            .execute()

        return GoogleDriveFileId(result.id)
    }

    private fun <TR>processDriveOperation(action: (drive: Drive, folder: GoogleDriveFileId) -> TR): TR {
        service?.let {
            return try {
                action(it.service, it.folder)
            } catch (ex: Exception) {
                Timber.e(ex)
                throw ex
            }
        } ?: throw IllegalStateException("Drive service is not ready")
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