package com.example.uilib

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.uilib.adapters.MovieAdapter
import com.example.uilib.databinding.MoviesActivityBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.uilib.models.Movie
import com.example.uilib.viewmodel.MoviesViewModel
import com.itzik.common.datamodels.Definitions
import kotlinx.android.synthetic.main.movies_activity.view.*
import java.util.*



class MoviesActivity : AppCompatActivity() {


    private val TAG: String = MoviesActivity::class.java.simpleName
    private val INTERNET_REQUEST_CODE = 101


    private lateinit var viewModel: MoviesViewModel
    private lateinit var binding: MoviesActivityBinding
    private lateinit var moviewAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.movies_activity)
        showRefresh()
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        binding.viewModel = viewModel
        createAdapter()

        // waiting for change movies and update activity
        viewModel.getMovieList().observe(this, Observer<List<Movie>> { movies ->
            movies?.let {
                stopRefresh()
                movies.toTypedArray()
                Log.d(TAG, Arrays.deepToString(movies.toTypedArray()))
                updateMovieList(movies)
            }
        })

        viewModel.getMovieSelect().observe(this, Observer<Movie> { movie ->
            movie?.let {
                Log.d(TAG, "getMovieSelect: $movie")
                val orientation: Int = resources.getConfiguration().orientation
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    completeInclude(movie)
                } else {
                    showWithAnimationMovieDescScreen(movie)
                }
            }
        })

        setupPermissions()
    }


    private fun completeInclude(movie: Movie) {
        val urlImage: String = String.format(
            Definitions.URL_IMAGE +"%s","${movie.posterPath}")
        Log.d(TAG,"URL? $urlImage")
        val view = binding.root.include_view
        Glide.with(this).load(urlImage).into(view.findViewById(R.id.poster))
        view.findViewById<TextView>(R.id.desc).text = movie.desc
        view.findViewById<TextView>(R.id.rate).text = movie.rate
        view.findViewById<TextView>(R.id.year).text = movie.date
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            INTERNET_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    viewModel.fetchAllMovies()
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
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


    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.INTERNET
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to Internet denied")
            makeRequest()
        } else {
            Log.i(TAG, "Permission to Internet granted")
            viewModel.fetchAllMovies()
        }
    }


    private fun createAdapter() {
        moviewAdapter = MovieAdapter(viewModel)
        val gridCount = resources.getInteger(R.integer.grid_colum)
        binding.recyclerView.layoutManager = GridLayoutManager(this, gridCount)
        binding.recyclerView.adapter = moviewAdapter
        Log.d(TAG, "createAdapter")
    }

    private fun updateMovieList(items: List<Movie>) {
        moviewAdapter.updateMovies(items)
        Log.d(TAG, "updatePeopleList")
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.INTERNET),
            INTERNET_REQUEST_CODE
        )
    }

    private fun showWithAnimationMovieDescScreen(movie: Movie) {
        Log.d(TAG, "showWithAnimationMovieDescScreen")
        var launchToMovieDescActivity = Intent(this, MovieDescActivity::class.java)
        launchToMovieDescActivity.putExtra(getString(R.string.movie_bundle_extra), movie)
        startActivity(launchToMovieDescActivity)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun showRefresh() {
       // binding.progressBar.visibility = View.VISIBLE
        binding.swipeRefresh.setRefreshing(true)
        binding.swipeRefresh.setOnRefreshListener(CallbackRefresh())
    }

    private fun stopRefresh() {
        //binding.progressBar.visibility = View.GONE
        binding.swipeRefresh.setRefreshing(false)
    }


    private inner class CallbackRefresh: SwipeRefreshLayout.OnRefreshListener{
        override fun onRefresh() {
            viewModel.fetchAllMovies()
           Log.d(TAG,"onRefresh")
        }

    }

}