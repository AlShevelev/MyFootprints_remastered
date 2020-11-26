package com.shevelev.my_footprints_remastered.sync.core

import com.shevelev.my_footprints_remastered.common_entities.sync.SyncOperation
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.sync.log_repositoty.SyncRecordRepository
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
    private val syncRecordRepository: SyncRecordRepository
) : SyncCore {
    /**
     * @return true in case of success
     */
    override fun sync(): Boolean =
        try {
            if(canSync()) {
                processSync()
            }
            true
        } catch (ex: Exception) {
            syncRecordRepository.clearMarks()
            Timber.e(ex)
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

        recordsToSync.forEach { record ->
            when(record.operation) {
                SyncOperation.CREATE -> processCreate(record.footprintId)
                SyncOperation.UPDATE -> processUpdate(record.footprintId)
                SyncOperation.DELETE -> processDelete(record.footprintId)
            }
            syncRecordRepository.deleteSyncRecord(record.id)
        }
    }

    private fun processCreate(footprintId: Long) {
        // Load footprint from Db
        // don't forget to fill GD file Id in FootprintDb
    }

    private fun processUpdate(footprintId: Long) {
        // Load footprint from Db
        // Don't forget to use isMetadataOnlyUpdated in a log record
    }

    private fun processDelete(footprintId: Long) {

    }
}