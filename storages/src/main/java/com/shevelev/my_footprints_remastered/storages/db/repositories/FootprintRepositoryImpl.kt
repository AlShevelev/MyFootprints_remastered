package com.shevelev.my_footprints_remastered.storages.db.repositories

import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.common_entities.GeoPoint
import com.shevelev.my_footprints_remastered.common_entities.PinColor
import com.shevelev.my_footprints_remastered.storages.db.core.DbCore
import com.shevelev.my_footprints_remastered.storages.db.entities.FootprintDb
import javax.inject.Inject

class FootprintRepositoryImpl
@Inject
constructor(
    private val db: DbCore
): FootprintRepository {
    override fun getCount(): Int = db.footprint.readCount()

    override fun getLast(): Footprint? = db.footprint.readLast()?.mapToEntity()

    override fun create(footprint: Footprint) = db.footprint.create(footprint.mapToDb())

    override fun update(footprint: Footprint) = db.footprint.update(footprint.mapToDb())

    override fun updateGeo(id: Long, city: String?, country: String?) = db.footprint.updateGeo(id, city, country)

    override fun updateGoogleDriveFileId(id: Long, gdFileId: String) = db.footprint.updateGoogleDriveFileId(id, gdFileId)

    override fun delete(id: Long) = db.footprint.delete(id)

    /**
     * Get all footprints sorted by creation moment in descending order
     */
    override fun getAll(): List<Footprint> = db.footprint.readAll().map { it.mapToEntity() }

    override fun getAllWithoutGeo(): List<Footprint> = db.footprint.readAllWithoutGeo().map { it.mapToEntity() }

    override fun getById(footprintId: Long): Footprint? = db.footprint.readById(footprintId)?.mapToEntity()

    private fun FootprintDb.mapToEntity() =
        Footprint(
            id = id,
            imageFileName = imageFileName,
            location = GeoPoint(latitude, longitude),
            comment = comment,
            pinColor = PinColor(pinTextColor, pinBackgroundColor),
            created = created,
            city = city,
            country = country,
            isGeoLoaded = isGeoLoaded,
            googleDriveFileId = googleDriveFileId
        )

    private fun Footprint.mapToDb() =
        FootprintDb(
            id = id,
            imageFileName = imageFileName,
            latitude = location.latitude,
            longitude = location.longitude,
            comment = comment,
            pinTextColor = pinColor.textColor,
            pinBackgroundColor = pinColor.backgroundColor,
            created = created,
            createdSort = created.toInstant().epochSecond,
            city = city,
            country = country,
            isGeoLoaded = isGeoLoaded,
            googleDriveFileId = googleDriveFileId
        )
}