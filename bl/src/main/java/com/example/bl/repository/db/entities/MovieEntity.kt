package com.example.bl.repository.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_items")
data class MovieEntity(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "poster") var poster: String,
    @ColumnInfo(name = "rate") var rate: String,
    @ColumnInfo(name = "date") var date: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0)
