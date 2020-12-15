package com.shevelev.my_footprints_remastered.sync.sync_core

import com.shevelev.my_footprints_remastered.common_entities.sync.SyncOperation
import com.shevelev.my_footprints_remastered.common_entities.sync.SyncRecord
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.storages.files.FilesHelper
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.sync.footprint_meta_gd_crypt.FootprintMetaGoogleDriveCrypt
import com.shevelev.my_footprints_remastered.common_entities.sync.GoogleDriveFileId
import com.shevelev.my_footprints_remastered.sync.gd_operations.GoogleDriveOperations
import com.shevelev.my_footprints_remastered.sync.db_repositories.SyncRecordRepository
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveCredentials
import com.shevelev.my_footprints_remastered.utils.connection.ConnectionHelper
import com.shevelev.my_footprints_remastered.utils.connection.ConnectionInfo
import timber.log.Timber
import javax.inject.Inject

class SyncCoreImpl
@Inject
constructor(
    private val keyValueStorage: KeyValueStorageFacade,
    private val connectionHelper: ConnectionHelper,
    private val gdCredentials: GoogleDriveCredentials,
    private val syncRecordRepository: SyncRecordRepository,
    private val footprintRepository: FootprintRepository,
    private val operations: GoogleDriveOperations,
    private val footprintMetadataCrypt: FootprintMetaGoogleDriveCrypt,
    private val filesHelper: FilesHelper
) : SyncCore {
    /**
     * @return true in case of success
     */
    override fun sync(): Boolean =
        try {
            Timber.tag("SYNC_TEST").d("SyncWorker created")
            if(canSync()) {
                Timber.tag("SYNC_TEST").d("Can sync - start syncing")
                processSync()
            }
            Timber.tag("SYNC_TEST").d("SyncWorker completed successfully")
            true
        } catch (ex: Exception) {
            syncRecordRepository.clearMarks()
            Timber.tag("SYNC_TEST").e(ex)
            Timber.tag("SYNC_TEST").d("SyncWorker completed unsuccessfully")
            false
        }

    private fun canSync(): Boolean =
        when(connectionHelper.getConnectionInfo()) {
            ConnectionInfo.NO_CONNECTION -> false

            ConnectionInfo.WI_FI -> {
                !gdCredentials.needToSingIn
            }

            ConnectionInfo.MOBILE -> {
                if(keyValueStorage.isUseWiFiToBackup()) {
                    false
                } else {
                    !gdCredentials.needToSingIn
                }
            }
        }

    private fun processSync() {
        val recordsToSync = syncRecordRepository.getAllAndMark()
        Timber.tag("SYNC_TEST").d("recordsToSync total: ${recordsToSync.size}")

        recordsToSync.forEach { record ->
            when(record.operation) {
                SyncOperation.CREATE -> processCreate(record)
                SyncOperation.UPDATE -> processUpdate(record)
                SyncOperation.DELETE -> processDelete(record)
            }
            Timber.tag("SYNC_TEST").d("delete sync record with id: ${record.id}")
            syncRecordRepository.deleteSyncRecord(record.id)
        }
    }

    private fun processCreate(syncRecord: SyncRecord) {
        Timber.tag("SYNC_TEST").d("Process Create record")
        footprintRepository.getById(syncRecord.footprintId)?.let { footprint ->
            val metadata = footprintMetadataCrypt.encrypt(footprint)
            val content = filesHelper.readFileContent(filesHelper.getOrCreateImageFile(footprint.imageFileName))
            val gdFileId = operations.create(metadata, content, footprint.imageFileName)

            footprintRepository.updateGoogleDriveFileId(footprint.id, gdFileId.id)
        }
    }

    private fun processUpdate(syncRecord: SyncRecord) {
        Timber.tag("SYNC_TEST").d("Process Update record")
        footprintRepository.getById(syncRecord.footprintId)?.let { footprint ->
            syncRecord.googleDriveFileId?.let {
                val gdFileId = GoogleDriveFileId(it)

                val isMetadataUpdated = syncRecord.isMetadataUpdated ?: true
                val isContentUpdated = syncRecord.isImageUpdated ?: true

                Timber.tag("SYNC_TEST").d("isMetadataUpdated: $isMetadataUpdated; isContentUpdated: $isContentUpdated")

                val metadata = if(isMetadataUpdated) {
                    footprintMetadataCrypt.encrypt(footprint)
                } else {
                    null
                }

                val content = if(isContentUpdated) {
                    filesHelper.readFileContent(filesHelper.getOrCreateImageFile(footprint.imageFileName))
                } else {
                    null
                }

                when {
                    isMetadataUpdated && !isContentUpdated -> operations.updateMetadata(metadata!!, gdFileId)
                    !isMetadataUpdated && isContentUpdated -> operations.updateContent(content!!,  gdFileId)
                    isMetadataUpdated && isContentUpdated -> operations.updateAll(metadata!!, content!!, gdFileId)
                }
            }
        }
    }

    private fun processDelete(syncRecord: SyncRecord) {
        Timber.tag("SYNC_TEST").d("Process Delete record")
        syncRecord.googleDriveFileId?.let {
            operations.delete(GoogleDriveFileId(it))
        }
    }
}