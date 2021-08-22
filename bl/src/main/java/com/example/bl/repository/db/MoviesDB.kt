package com.example.bl.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bl.repository.db.entities.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)


abstract class MoviesDB: RoomDatabase(){

    abstract fun MoviesDao(): MoviesDao
    companion object {

        private const val DB_NAME: String = "Movies_list.db"

        @Volatile private var instance: MoviesDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
             MoviesDB::class.java, DB_NAME)
            .build()
    }

}