package com.example.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "site_table")
class Site(@PrimaryKey
           @ColumnInfo(name = "name") val name: String,
           @ColumnInfo(name = "url") val url: String,
           @ColumnInfo(name = "position") val position: Int)