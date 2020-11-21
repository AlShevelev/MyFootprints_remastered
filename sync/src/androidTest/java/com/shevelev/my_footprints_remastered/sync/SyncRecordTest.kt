package com.shevelev.my_footprints_remastered.sync

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.shevelev.my_footprints_remastered.common_entities.sync.SyncOperation
import com.shevelev.my_footprints_remastered.storages.db.builder.DatabaseBuilder
import com.shevelev.my_footprints_remastered.sync.log_repositoty.SyncRecordRepository
import com.shevelev.my_footprints_remastered.sync.log_repositoty.SyncRecordRepositoryImpl
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

/**
 *  Tests for [SyncRecordRepository]
 */
@RunWith(AndroidJUnit4::class)
class SyncRecordTest {
    companion object {
        private lateinit var repository: SyncRecordRepositoryImpl

        @BeforeClass
        @JvmStatic
        fun init() {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val dbCore = DatabaseBuilder.build(context)
            repository = SyncRecordRepositoryImpl(dbCore)
        }
    }

    @After
    fun release() {
        repository.clear()
    }

    @Test
    fun create() {
        // Arrange
        val footprintId = 1L

        // Act
        repository.addCreateRecord(footprintId)

        // Assert
        val allRecords = repository.getAll()

        assertTrue(allRecords.isNotEmpty())
        assertEquals(1, allRecords.size)

        val dbRecord = allRecords.first()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.CREATE)
    }

    @Test
    fun updateEmpty() {
        // Arrange
        val footprintId = 1L

        // Act
        repository.addUpdateRecord(footprintId)

        // Assert
        val allRecords = repository.getAll()

        assertTrue(allRecords.isNotEmpty())
        assertEquals(1, allRecords.size)

        val dbRecord = allRecords.first()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.UPDATE)
    }

    @Test
    fun updateAfterUpdate() {
        // Arrange
        val footprintId = 1L
        repository.addUpdateRecord(footprintId)

        // Act
        repository.addUpdateRecord(footprintId)

        // Assert
        val allRecords = repository.getAll()

        assertTrue(allRecords.isNotEmpty())
        assertEquals(1, allRecords.size)

        val dbRecord = allRecords.first()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.UPDATE)
    }

    @Test
    fun updateAfterCreate() {
        // Arrange
        val footprintId = 1L
        repository.addCreateRecord(footprintId)

        // Act
        repository.addUpdateRecord(footprintId)

        // Assert
        val allRecords = repository.getAll()

        assertTrue(allRecords.isNotEmpty())
        assertEquals(1, allRecords.size)

        val dbRecord = allRecords.first()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.CREATE)
    }

    @Test
    fun updateAfterCreateInProgress() {
        // Arrange
        val footprintId = 1L
        repository.addCreateRecord(footprintId)
        repository.getAllAndMark()              // Mark the record as "sync in progress"

        // Act
        repository.addUpdateRecord(footprintId)

        // Assert
        val allRecords = repository.getAll()

        assertTrue(allRecords.isNotEmpty())
        assertEquals(2, allRecords.size)

        var dbRecord = allRecords.first()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.CREATE)

        dbRecord = allRecords.last()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.UPDATE)
    }

    @Test
    fun updateAfterCreateInProgressAndCancel() {
        // Arrange
        val footprintId = 1L
        repository.addCreateRecord(footprintId)
        repository.getAllAndMark()              // Mark the record as "sync in progress"

        // Act
        repository.addUpdateRecord(footprintId)
        repository.clearMarks()
        repository.addUpdateRecord(footprintId)

        // Assert
        val allRecords = repository.getAll()

        assertTrue(allRecords.isNotEmpty())
        assertEquals(2, allRecords.size)

        var dbRecord = allRecords.first()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.CREATE)

        dbRecord = allRecords.last()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.UPDATE)
    }

    @Test
    fun updateAfterDelete() {
        // Arrange
        val footprintId = 1L
        repository.addDeleteRecord(footprintId)

        // Act
        repository.addUpdateRecord(footprintId)

        // Assert
        val allRecords = repository.getAll()

        assertTrue(allRecords.isNotEmpty())
        assertEquals(1, allRecords.size)

        val dbRecord = allRecords.first()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.DELETE)
    }

    @Test
    fun deleteEmpty() {
        // Arrange
        val footprintId = 1L

        // Act
        repository.addDeleteRecord(footprintId)

        // Assert
        val allRecords = repository.getAll()

        assertTrue(allRecords.isNotEmpty())
        assertEquals(1, allRecords.size)

        val dbRecord = allRecords.first()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.DELETE)
    }

    @Test
    fun deleteAfterUpdate() {
        // Arrange
        val footprintId = 1L
        repository.addUpdateRecord(footprintId)

        // Act
        repository.addDeleteRecord(footprintId)

        // Assert
        val allRecords = repository.getAll()

        assertTrue(allRecords.isNotEmpty())
        assertEquals(1, allRecords.size)

        val dbRecord = allRecords.first()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.DELETE)
    }

    @Test
    fun deleteAfterCreate() {
        // Arrange
        val footprintId = 1L
        repository.addCreateRecord(footprintId)

        // Act
        repository.addDeleteRecord(footprintId)

        // Assert
        val allRecords = repository.getAll()

        assertTrue(allRecords.isEmpty())
    }

    @Test
    fun deleteAfterCreateInProgress() {
        // Arrange
        val footprintId = 1L
        repository.addCreateRecord(footprintId)
        repository.getAllAndMark()          // Mark the record as "sync in progress"

        // Act
        repository.addDeleteRecord(footprintId)

        // Assert
        val allRecords = repository.getAll()

        assertTrue(allRecords.isNotEmpty())
        assertEquals(2, allRecords.size)

        var dbRecord = allRecords.first()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.CREATE)

        dbRecord = allRecords.last()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.DELETE)
    }

    @Test
    fun deleteAfterDelete() {
        // Arrange
        val footprintId = 1L
        repository.addDeleteRecord(footprintId)

        // Act
        repository.addDeleteRecord(footprintId)

        // Assert
        val allRecords = repository.getAll()

        assertTrue(allRecords.isNotEmpty())
        assertEquals(1, allRecords.size)

        val dbRecord = allRecords.first()
        assertEquals(dbRecord.footprintId, footprintId)
        assertEquals(dbRecord.operation, SyncOperation.DELETE)
    }
}

// Test a case when we can have a several records with the same footprintId (when we start syncing, update log and update sync process)
