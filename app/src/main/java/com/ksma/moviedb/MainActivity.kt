package com.ksma.moviedb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ksma.moviedb.databinding.ActivityMainBinding
import com.ksma.moviedb.ui.movielist.MovieListFragment
import com.ksma.moviedb.ui.movielist.MovieListViewModel
import com.ksma.moviedb.utils.MovieType
import dagger.hilt.android.AndroidEntryPoint
//REF: https://github.com/OsmarICancino/MovieHunter
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        val upComingFragment = MovieListFragment.newInstance(MovieType.UPCOMING_MOVIE)
        val transactionFour = supportFragmentManager.beginTransaction()
        transactionFour.replace(binding.upComingContainer.id, upComingFragment)
        transactionFour.commit()

        val popularMovieFragment = MovieListFragment.newInstance(MovieType.POPULAR_MOVIE)
        val transactionThree = supportFragmentManager.beginTransaction()
        transactionThree.replace(binding.popularContainer.id, popularMovieFragment)
        transactionThree.commit()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(
            R.anim.anim_fade_in, R.anim.anim_fade_out
        )
    }
}