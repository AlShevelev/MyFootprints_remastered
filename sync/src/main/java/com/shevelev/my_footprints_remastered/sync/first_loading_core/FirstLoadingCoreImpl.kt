package com.shevelev.my_footprints_remastered.sync.first_loading_core

import com.shevelev.my_footprints_remastered.common_entities.sync.GoogleDriveFileId
import com.shevelev.my_footprints_remastered.storages.db.repositories.FootprintRepository
import com.shevelev.my_footprints_remastered.storages.files.FilesHelper
import com.shevelev.my_footprints_remastered.storages.key_value.KeyValueStorageFacade
import com.shevelev.my_footprints_remastered.sync.db_repositories.FirstLoadRecordRepository
import com.shevelev.my_footprints_remastered.sync.footprint_meta_gd_crypt.FootprintMetaGoogleDriveCrypt
import com.shevelev.my_footprints_remastered.sync.gd_operations.GoogleDriveOperations
import com.shevelev.my_footprints_remastered.sync.gd_sign_in.GoogleDriveCredentials
import com.shevelev.my_footprints_remastered.utils.connection.ConnectionHelper
import com.shevelev.my_footprints_remastered.utils.connection.ConnectionInfo
import com.shevelev.my_footprints_remastered.utils.id_hash.IdUtil
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class FirstLoadingCoreImpl
@Inject
constructor(
    private val firstLoadRecordRepository: FirstLoadRecordRepository,
    private val footprintRepository: FootprintRepository,
    private val googleDriveOperations: GoogleDriveOperations,
    private val googleDriveCredentials: GoogleDriveCredentials,
    private val filesHelper: FilesHelper,
    private val keyValueStorage: KeyValueStorageFacade,
    private val footprintMetaGoogleDriveCrypt: FootprintMetaGoogleDriveCrypt,
    private val connectionHelper: ConnectionHelper
) : FirstLoadingCore {

    private lateinit var messageSender: FirstLoadingServiceMessageSender

    override fun setMessageSender(sender: FirstLoadingServiceMessageSender) {
        messageSender = sender
    }

    override fun load() {
        try {
            if(connectionHelper.getConnectionInfo() == ConnectionInfo.NO_CONNECTION || googleDriveCredentials.needToSingIn) {
                messageSender.sendFail()
                return
            }

            messageSender.sendListLoadStarted()
            val filesToLoad = getFilesToLoad()
            messageSender.sendListLoadCompleted()

            filesToLoad.forEachIndexed { index, file ->
                loadFile(file)
                messageSender.sendProgress(index, filesToLoad.size)
            }

            keyValueStorage.setStartLoadingCompleted(true)
            messageSender.sendSuccess()
        } catch (ex: Exception) {
            Timber.e(ex)
            messageSender.sendFail()
        }
    }

    private fun getFilesToLoad(): List<GoogleDriveFileId> {
        var filesToLoad = firstLoadRecordRepository.get()
        if(filesToLoad.isEmpty()) {
            filesToLoad = googleDriveOperations.readFilesList()

            if(filesToLoad.isNotEmpty()) {
                firstLoadRecordRepository.add(filesToLoad)
            }
        }

        return filesToLoad
    }

    private fun loadFile(fileId: GoogleDriveFileId) {
        var localImageFile: File? = null
        val dbId = IdUtil.generateLongId()

        try {
            // Load file from GD
            val gdFile = googleDriveOperations.read(fileId)

            // Save local image
            localImageFile = filesHelper.getOrCreateImageFile(gdFile.name)
            filesHelper.saveBitesToFile(gdFile.content, localImageFile)

            // Save footprint to Db
            var footprint = footprintMetaGoogleDriveCrypt.decrypt(gdFile.metadata)
            footprint = footprint.copy(
                id = dbId,
                imageFileName = gdFile.name,
                city = null,
                country = null,
                isGeoLoaded = false,
                googleDriveFileId = fileId.id
            )
            footprintRepository.create(footprint)

            // Remove log record
            firstLoadRecordRepository.delete(fileId)
        } catch (ex: Exception) {
            localImageFile?.delete()
            footprintRepository.delete(dbId)

            throw ex
        }
    }
}