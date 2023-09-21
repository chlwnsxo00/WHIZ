package com.example.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SiteDao {
    @Query("SELECT * FROM memoTable ORDER BY id DESC")
    fun getAll(): LiveData<List<Site>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(memo: Site)

    @Update
    fun update(memo: Site)

    @Delete
    fun delete(memo: Site)

    @Query("DELETE FROM memoTable")
    fun deleteAll()
}
