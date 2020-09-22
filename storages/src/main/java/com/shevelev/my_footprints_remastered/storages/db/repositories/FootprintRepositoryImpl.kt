package com.shevelev.my_footprints_remastered.storages.db.repositories

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

    private fun FootprintDb.mapToFootprint() =
        Footprint(
            id = id,
            imageContentUri = imageContentUri,
            latitude = latitude,
            longitude = longitude,
            comment = comment,
            pinTextColor = pinTextColor,
            pinBackgroundColor = pinBackgroundColor,
            created = created,
        )

    private fun Footprint.mapToFootprintDb() =
        FootprintDb(
            id = id,
            imageContentUri = imageContentUri,
            latitude = latitude,
            longitude = longitude,
            comment = comment,
            pinTextColor = pinTextColor,
            pinBackgroundColor = pinBackgroundColor,
            created = created,
            createdSort = created.toInstant().epochSecond
        )
}