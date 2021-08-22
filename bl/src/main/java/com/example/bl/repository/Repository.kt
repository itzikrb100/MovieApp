package com.example.bl.repository

import com.example.bl.repository.db.entities.MovieEntity
import kotlinx.coroutines.flow.Flow


interface Repository{
    fun requestList(resEvent: ResponseEvent)
    fun requestList()
    fun getMoviesItem(): Flow<List<MovieEntity>>
    fun closeRepository()
}