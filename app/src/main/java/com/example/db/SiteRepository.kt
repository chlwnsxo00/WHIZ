package com.example.db

import androidx.annotation.WorkerThread
import com.example.room.Site
import com.example.room.SiteDao
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class SiteRepository(private val siteDao: SiteDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allSites: Flow<List<Site>> = siteDao.getAll()
    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(site: Site) {
        siteDao.insert(site)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(site: Site) {
        siteDao.delete(site)
    }
}