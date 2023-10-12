package com.example.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Site::class), version = 1, exportSchema = false)
abstract class SiteRoomDatabase: RoomDatabase(){
    abstract fun siteDao(): SiteDao

    companion object{
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SiteRoomDatabase? = null

        fun getDatabase( context: Context,
                         scope: CoroutineScope
        ): SiteRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SiteRoomDatabase::class.java,
                    "site_database"
                ).addCallback(SiteDatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class SiteDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.siteDao())
                }
            }
        }

        suspend fun populateDatabase(siteDao: SiteDao) {
            // Delete all content here.
            siteDao.deleteAll()

            // Add sample words.
            var site = Site("Bloomberg","https://www.bloomberg.com/markets",0)
            siteDao.insert(site)
            site = Site("CNBC", "https://www.cnbc.com/world/?region=world",1)
            siteDao.insert(site)
            site = Site("Google finance", "https://www.google.com/finance/",2)
            siteDao.insert(site)
            site = Site("yahoo!finance",
                "https://finance.yahoo.com/?guccounter=1&guce_referrer=aHR0cHM6Ly93d3cuZ29vZ2xlLmNvbS8&guce_referrer_sig=AQAAAKB0BfHIF_aP13mToUmDLG_gMUpyz31uzhO03q1FeVx4SlkRq9bKPJNdcqWUH5wOKooDTauDD0aK2gWVn2rrgwEvqSbZL6IyHNTCjp0Baa6w9JTs2Czh249aP5pWJL4H2M714Oh9_3usJyzT1USs4ZiSYGvM1o7Z1rdx62-Np1YM"
            ,3)
            siteDao.insert(site)
            site = Site("FINVIZ", "https://finviz.com/map.ashx?t=sec",4)
            siteDao.insert(site)
        }
    }
}