package com.ksma.moviedb.ui.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ksma.moviedb.R
import com.ksma.moviedb.data.model.MovieInfo
import com.ksma.moviedb.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    private lateinit var movieInfo: MovieInfo

    companion object {
        fun newInstance(context: Context, movieInfo: MovieInfo): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("info", movieInfo)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieInfo = intent.getSerializableExtra("info") as MovieInfo

        setup()
    }

    override fun onStart() {
        super.onStart()
        //  favouriteMovieRelay.onNext(0)
    }

    private var isSaveFavourite = false
    private fun setup() {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movieInfo.poster_path}")
            .placeholder(R.drawable.movie_placeholder)
            .fitCenter()
            .into(binding.ivPoster)

        binding.tvDate.text = movieInfo.release_date
        binding.tvTitle.text = movieInfo.title
        binding.tvOverview.text = movieInfo.overview

        //To handle Favourite movie data by ROOM Db
        binding.ivFavourite.setOnClickListener {
            isSaveFavourite = if (!isSaveFavourite) {
                Glide.with(this).load(R.drawable.ic_baseline_favorite_24)
                    .placeholder(R.drawable.ic_baseline_favorite_border_24)
                    .into(binding.ivFavourite)
                true
            } else {
                Glide.with(this).load(R.drawable.ic_baseline_favorite_border_24)
                    .placeholder(R.drawable.ic_baseline_favorite_border_24)
                    .into(binding.ivFavourite)
                false
            }
        }
    }

    private fun renderMovieList(movies: List<MovieInfo>) {
        binding.progressBar.progress.visibility = View.GONE
        if (movies.isNotEmpty()) {
            isSaveFavourite = if (movies.contains(movieInfo)) {
                Glide.with(this).load(R.drawable.ic_baseline_favorite_24)
                    .placeholder(R.drawable.ic_baseline_favorite_border_24)
                    .into(binding.ivFavourite)
                true
            } else {
                Glide.with(this).load(R.drawable.ic_baseline_favorite_border_24)
                    .placeholder(R.drawable.ic_baseline_favorite_border_24)
                    .into(binding.ivFavourite)
                false
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.anim_fade_in, R.anim.anim_fade_out
        )
    }
}