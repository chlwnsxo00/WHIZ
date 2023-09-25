package com.example.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SiteDao {
    @Query("SELECT * FROM site_table")
    fun getAll(): Flow<List<Site>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Site)

    @Delete
    suspend fun delete(site: Site)

    @Query("DELETE FROM site_table")
    suspend fun deleteAll()
}
