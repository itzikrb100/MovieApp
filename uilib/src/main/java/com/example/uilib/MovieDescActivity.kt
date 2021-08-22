package com.example.uilib

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.uilib.databinding.MovieDescActivityBinding
import com.example.uilib.models.Movie
import com.itzik.common.datamodels.Definitions.Companion.URL_IMAGE
import com.itzik.interfaces.datamodels.MovieData

class MovieDescActivity: AppCompatActivity() {

    private val TAG: String = MovieDescActivity::class.java.simpleName

    private lateinit var binding: MovieDescActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, R.layout.movie_desc_activity)
        initMovieView()
        Log.d(TAG,"onCreate:")
    }

    override fun onBackPressed() {
        Log.d(TAG,"onBackPressed")
        finish()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "OnResume")
    }


    override fun onPause() {
        super.onPause()
        Log.d(TAG, "OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "OnDestroy")
    }

    private fun initMovieView(){
        val movieExtra = intent.getParcelableExtra<Movie>(getString(R.string.movie_bundle_extra))
        Log.d(TAG,"movieExtra: $movieExtra")
        val urlImage: String = String.format(URL_IMAGE+"%s",
            movieExtra!!.posterPath)
        Log.d(TAG,"URL? $urlImage")
        Glide.with(this).load(urlImage).into(binding.poster)
        binding.desc.text = movieExtra.desc
        binding.rate.text = movieExtra.rate
        binding.year.text = movieExtra.date
    }
}