package com.example.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Site::class), version = 1, exportSchema = false)
abstract class SiteDatabase: RoomDatabase(){
    abstract fun siteDao(): SiteDao

    companion object{
        private var INSTANCE: SiteDatabase? = null
        fun getInstance(context: Context): SiteDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    SiteDatabase::class.java,
                    "site_database")
                    .build()
            }
            return INSTANCE as SiteDatabase
        }
    }

}