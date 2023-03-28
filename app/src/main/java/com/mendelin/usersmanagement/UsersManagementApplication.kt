package com.mendelin.usersmanagement

import android.app.Application
import com.mendelin.usersmanagement.domain.logging.TimberPlant
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UsersManagementApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TimberPlant.plantTimberDebugLogger()
    }
}