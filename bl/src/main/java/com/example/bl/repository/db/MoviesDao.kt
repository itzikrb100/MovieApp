package com.example.bl.repository.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bl.repository.db.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies_items")
    fun getAll(): Flow<List<MovieEntity>>

    @Insert
    fun insertAll(movies: List<MovieEntity>)

    @Query("DELETE FROM  movies_items")
    fun deleteAll()
}