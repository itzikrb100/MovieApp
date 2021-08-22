package com.example.bl.repository

import android.content.Context
import android.util.Log
import com.example.bl.networkRequest.APIItemInterface
import com.example.bl.networkRequest.ApiClient
import com.example.bl.repository.db.MoviesDB
import com.example.bl.repository.db.entities.MovieEntity
import com.example.bl.tasks.ITaskRunner
import com.example.bl.tasks.IoTaskRunner
import com.itzik.common.datamodels.Definitions.Companion.LIMIT_NUMBER
import com.itzik.common.datamodels.ResponseData
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository(ctx: Context): Repository {

    private val task: ITaskRunner
    private val movieDb: MoviesDB


    private var apiMovieClient: APIItemInterface? = null

    init {
        apiMovieClient = ApiClient.getInstance()?.createApi()
        task = IoTaskRunner()
        movieDb = MoviesDB.invoke(ctx)
        Log.d(TAG,"INIT")
    }

    override fun closeRepository(){
        Log.d(TAG,"closeRepository")
        task.cancel()
    }




    companion object {
        private const val TAG: String = "MovieRepository"

        private  var INSTANCE: MovieRepository? = null


        @Synchronized fun getInstance(ctx: Context): MovieRepository{
           if(INSTANCE == null)
                INSTANCE = MovieRepository(ctx)

           return INSTANCE!!
        }
    }

    override fun requestList(resEvent: ResponseEvent) {
        requestMovieList(resEvent)
    }

    override fun requestList() {
        requestMovieList()
    }

    override fun getMoviesItem(): Flow<List<MovieEntity>> {
       return movieDb.MoviesDao().getAll()
    }


    private fun requestMovieList(event: ResponseEvent){
        Log.d(TAG,"requestMovieList")
        var call: Call<ResponseData>? =  apiMovieClient?.doGetListItems()
        call?.enqueue(cb)
    }

    private fun requestMovieList(){
        Log.d(TAG,"requestMovieList")
        var call: Call<ResponseData>? =  apiMovieClient?.doGetListItems()
        call?.enqueue(cb)
    }


    private val funMovie: suspend(subList: MutableList<MovieEntity>?) -> Unit = {
        movieDb.MoviesDao().deleteAll()
        it?.apply {  movieDb.MoviesDao().insertAll(it) }
        Log.d(TAG,"apply fun movie")
    }

     private val cb =   object: Callback<ResponseData> {
         override fun onResponse(call: Call<ResponseData>?, response: Response<ResponseData>?) {
              val res: ResponseData? =  response?.body()
              Log.d(TAG,"code = ${response?.code()}")
              Log.d(TAG,"movies = ${res?.result}")
              Log.d(TAG," total result = ${res?.totalResult}")
              val subMovies: MutableList<MovieEntity>? = res?.result?.subList(0, LIMIT_NUMBER)?.map {
                      r -> MovieEntity(r.title, r.desc, r.posterPath, r.rate, r.date)}?.toMutableList()
              Log.d(TAG,"sub movies size = ${subMovies?.size}")
              task.run(subMovies, funMovie)
         }

         override fun onFailure(call: Call<ResponseData>?, t: Throwable?) {
             Log.e(TAG,"onFailure: cause = $t")
         }
     }

}