package com.ksma.moviedb.ui.movielist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ksma.moviedb.R
import com.ksma.moviedb.data.model.MovieInfo

class MovieListRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = arrayListOf<MovieInfo>()

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(movies: List<MovieInfo>) {
        list.clear()
        list.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_info, parent, false)
        return VHMovieInfo(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VHMovieInfo -> holder.bind(list[position], listener!!)
        }
    }

    inner class VHMovieInfo(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(info: MovieInfo, listener: OnItemClickListener) {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${info.poster_path}")
                .placeholder(R.drawable.movie_placeholder)
                .into(itemView.findViewById(R.id.ivPoster))

            itemView.findViewById<ImageView>(R.id.ivPoster).setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(info)
                }
            }
        }
    }
}

interface OnItemClickListener {
    fun onItemClick(movieInfo: MovieInfo)
    fun onFavouriteClick(movieInfo: MovieInfo)
}