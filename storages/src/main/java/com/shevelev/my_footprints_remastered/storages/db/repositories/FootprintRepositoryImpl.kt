package com.shevelev.my_footprints_remastered.storages.db.repositories

import android.net.Uri
import com.shevelev.my_footprints_remastered.common_entities.Footprint
import com.shevelev.my_footprints_remastered.storages.db.core.DbCore
import com.shevelev.my_footprints_remastered.storages.db.entities.FootprintDb
import javax.inject.Inject

class FootprintRepositoryImpl
@Inject
constructor(
    private val db: DbCore
): FootprintRepository {
    override fun getCount(): Int = db.footprint.readCount()

    override fun getLast(): Footprint? = db.footprint.readLast()?.mapToFootprint()

    override fun create(footprint: Footprint) = db.footprint.create(footprint.mapToFootprintDb())

    override fun update(footprint: Footprint) = db.footprint.update(footprint.mapToFootprintDb())

    override fun updateGeo(id: Long, city: String?, country: String?) = db.footprint.updateGeo(id, city, country)

    override fun delete(id: Long) = db.footprint.delete(id)

    /**
     * Get all footprints sorted by creation moment in descending order
     */
    override fun getAll(): List<Footprint> = db.footprint.readAll().map { it.mapToFootprint() }

    override fun getAllWithoutGeo(): List<Footprint> = db.footprint.readAllWithoutGeo().map { it.mapToFootprint() }

    private fun FootprintDb.mapToFootprint() =
        Footprint(
            id = id,
            imageContentUri = Uri.parse(imageContentUri),
            imageFileName = imageFileName,
            latitude = latitude,
            longitude = longitude,
            comment = comment,
            pinTextColor = pinTextColor,
            pinBackgroundColor = pinBackgroundColor,
            created = created,
            city = city,
            country = country,
            isGeoLoaded = isGeoLoaded
        )

    private fun Footprint.mapToFootprintDb() =
        FootprintDb(
            id = id,
            imageContentUri = imageContentUri.toString(),
            imageFileName = imageFileName,
            latitude = latitude,
            longitude = longitude,
            comment = comment,
            pinTextColor = pinTextColor,
            pinBackgroundColor = pinBackgroundColor,
            created = created,
            createdSort = created.toInstant().epochSecond,
            city = city,
            country = country,
            isGeoLoaded = isGeoLoaded
        )
}