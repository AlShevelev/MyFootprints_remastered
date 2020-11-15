package com.shevelev.my_footprints_remastered.utils.location

import android.location.Location
import android.location.LocationManager
import com.google.android.gms.maps.model.LatLng
import com.shevelev.my_footprints_remastered.common_entities.GeoPoint
import org.threeten.bp.Instant

fun Location.toMapLocation(): LatLng = LatLng(latitude, longitude)

fun Location.toGeoPoint(): GeoPoint = GeoPoint(latitude, longitude)

fun GeoPoint.toMapLocation(): LatLng = LatLng(latitude, longitude)

fun GeoPoint.toAndroidLocation(): Location = toMapLocation().toAndroidLocation()

fun LatLng.toAndroidLocation(): Location =
    Location(LocationManager.GPS_PROVIDER)
        .also {
            it.latitude = latitude
            it.longitude = longitude
            it.time = Instant.now().epochSecond
        }
