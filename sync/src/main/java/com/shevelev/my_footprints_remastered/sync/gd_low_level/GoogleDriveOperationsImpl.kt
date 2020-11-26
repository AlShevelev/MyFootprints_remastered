package com.shevelev.my_footprints_remastered.sync.gd_low_level

import android.util.Base64
import com.google.api.client.http.ByteArrayContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveCredentials
import timber.log.Timber
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class GoogleDriveOperationsImpl
@Inject
constructor(
    private val appName: String,
    private val gdCredentials: GoogleDriveCredentials
) : GoogleDriveOperations {

    private companion object {
        private const val SPACE = "appDataFolder"
        private const val METADATA = "footprints_metadata"
        private const val MIME_TYPE = "application/octet-stream"
    }

    private val service: Drive? by lazy { createService() }

    override fun create(metadata: ByteArray, content: ByteArray, fileName: String): GoogleDriveFileId =
        processDriveOperation { service ->
            val fileMetadata = File()

            fileMetadata.name = fileName
            fileMetadata.parents = listOf(SPACE)
            fileMetadata.properties = mapOf(METADATA to metadata.toDriveString())

            val fileContent = ByteArrayContent(MIME_TYPE, content)

            val result = service.files().create(fileMetadata, fileContent).execute()
            GoogleDriveFileId(result.id)
        }

    override fun read(fileId: GoogleDriveFileId): GoogleDriveFile =
        processDriveOperation { service ->
            val fileRequest = service
                .files()
                .get(fileId.id)
                .setFields("name, properties")

            val fileMetadata = fileRequest.execute()
            val metadata = fileMetadata.properties[METADATA]!!.fromDriveString()

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

    override fun updateMetadata(metadata: ByteArray, fileId: GoogleDriveFileId): Unit =
        processDriveOperation { service ->
            val oldFileMetadata = service
                .files()
                .get(fileId.id)
                .setFields("name")
                .execute()

            val newFileMetadata = File()
            newFileMetadata.name = oldFileMetadata.name
            newFileMetadata.properties = mapOf(METADATA to metadata.toDriveString())

            service.files().update(fileId.id, newFileMetadata).execute()
        }

    override fun updateContent(content: ByteArray, fileId: GoogleDriveFileId): Unit =
        processDriveOperation { service ->
            val oldFileMetadata = service
                .files()
                .get(fileId.id)
                .setFields("name, properties")
                .execute()

            val newFileMetadata = File()
            newFileMetadata.name = oldFileMetadata.name
            newFileMetadata.properties = oldFileMetadata.properties

            val fileContent = ByteArrayContent(MIME_TYPE, content)

            service.files().update(fileId.id, newFileMetadata, fileContent).execute()
        }

    override fun updateAll(metadata: ByteArray, content: ByteArray, fileId: GoogleDriveFileId): Unit =
        processDriveOperation { service ->
            val oldFileMetadata = service
                .files()
                .get(fileId.id)
                .setFields("name")
                .execute()

            val newFileMetadata = File()
            newFileMetadata.name = oldFileMetadata.name
            newFileMetadata.properties = mapOf(METADATA to metadata.toDriveString())

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

    private fun ByteArray.toDriveString() = Base64.encodeToString(this, Base64.DEFAULT)

    private fun String.fromDriveString() = Base64.decode(this, Base64.DEFAULT)
}