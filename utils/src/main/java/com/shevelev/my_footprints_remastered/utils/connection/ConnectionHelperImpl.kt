package com.shevelev.my_footprints_remastered.utils.connection

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class ConnectionHelperImpl
@Inject
constructor(
    private val appContext: Context
): ConnectionHelper {
    override fun getConnectionInfo(): ConnectionInfo {
        val cm = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (cm.activeNetwork == null) {
            ConnectionInfo.NO_CONNECTION
        } else {
            if (cm.isActiveNetworkMetered) {
                ConnectionInfo.MOBILE
            } else {
                ConnectionInfo.WI_FI
            }
        }
    }
}