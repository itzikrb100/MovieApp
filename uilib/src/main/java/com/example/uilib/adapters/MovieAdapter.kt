package com.example.uilib.adapters

import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uilib.R
import com.example.uilib.models.Movie
import com.example.uilib.viewmodel.MoviesViewModel
import com.itzik.common.datamodels.Definitions


class MovieAdapter(movieViewModel: MoviesViewModel,var movieslist: ArrayList<Movie> = ArrayList()):
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {



    private var dataList: ArrayList<Movie> = movieslist
    private var viewModel: MoviesViewModel = movieViewModel



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var view: ViewDataBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.item_movie, parent, false)
        Log.d(TAG, "onCreateViewHolder")
        return MovieViewHolder(view)
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        var movie: Movie = dataList[position]
        Log.d(TAG, "onBindViewHolder: moview - $movie")
        holder.bind(movie, viewModel)
    }


    override fun getItemCount(): Int {
       return  movieslist.size
    }

    fun updateMovies(newUsers: List<Movie>) {
        Log.d(TAG,"updatePeople")
        dataList.clear()
        dataList.addAll(newUsers)
        notifyDataSetChanged()
    }

   class MovieViewHolder(view: ViewDataBinding) : RecyclerView.ViewHolder(view.root) {
        val movieView: ViewDataBinding = view
        fun bind(movie: Movie,viewModel: MoviesViewModel) {
            val click = object: View.OnClickListener{
                override fun onClick(v: View?) {
                    viewModel.onClickItem(movie)
                }
            }
            loadPoster(movie, movieView.root.findViewById<ImageButton>(R.id.poster), click)
            movieView.root.findViewById<TextView>(R.id.name).text = "${movie.title}"
            movieView.root.findViewById<RelativeLayout>(R.id.item_container).setOnClickListener(click)
        }

       private fun loadPoster(movie: Movie, view: ImageView, click: View.OnClickListener) {
           val urlImage: String = String.format(
               Definitions.URL_IMAGE +"%s","${movie.posterPath}")
           Log.d(TAG,"URL? $urlImage")
           Glide.with(movieView.root.context).load(urlImage).into(view)
           view.setOnClickListener(click)
       }
    }

    companion object {
        private val TAG: String = MovieAdapter::class.java.simpleName
    }


}