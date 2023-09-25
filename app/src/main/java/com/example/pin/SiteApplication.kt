package com.example.pin

import android.app.Application
import com.example.db.SiteRepository
import com.example.room.SiteRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SiteApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { SiteRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { SiteRepository(database.siteDao()) }
}