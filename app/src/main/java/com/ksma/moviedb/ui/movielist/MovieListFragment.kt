package com.ksma.moviedb.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksma.moviedb.BuildConfig
import com.ksma.moviedb.R
import com.ksma.moviedb.data.model.MovieInfo
import com.ksma.moviedb.databinding.FragmentMovieListBinding
import com.ksma.moviedb.ui.moviedetail.MovieDetailActivity
import com.ksma.moviedb.utils.MovieType
import com.ksma.moviedb.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private lateinit var mMovieType: MovieType
    private var mMovieId: Int = 0

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val movieListViewModel by viewModels<MovieListViewModel>()
    private val mMovieAdapter: MovieListRecyclerAdapter by lazy { MovieListRecyclerAdapter() }

    companion object {
        fun newInstance(type: MovieType): MovieListFragment {
            val fragment = MovieListFragment()
            fragment.mMovieType = type
            return fragment
        }

        fun newInstance(type: MovieType, movieId: Int): MovieListFragment {
            val fragment = MovieListFragment()
            fragment.mMovieType = type
            fragment.mMovieId = movieId
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onStart() {
        super.onStart()
        setup()
        fetchMovieList()
    }

    private fun setup() {
        binding.rvMovie.setHasFixedSize(true)
        binding.rvMovie.isNestedScrollingEnabled = false
        binding.rvMovie.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovie.apply {
            adapter = mMovieAdapter
        }

        binding.ivRefresh.setOnClickListener {
            fetchMovieList()
        }

        mMovieAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(movieInfo: MovieInfo) {
                  startActivity(MovieDetailActivity.newInstance(requireContext(), movieInfo))
            }

            override fun onFavouriteClick(movieInfo: MovieInfo) {
                Toast.makeText(requireContext(), "FavouritClick", Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun fetchMovieList() {
        when (mMovieType) {

            MovieType.POPULAR_MOVIE -> {
                binding.tvTitle.text = getString(R.string.popular)
                movieListViewModel.fetchPopularMovie(apikey = BuildConfig.MOVIE_DB_KEY, page = 1)
                movieListViewModel.moviePopular.observe(viewLifecycleOwner, Observer {
                    when (it) {
                        is Resource.Success -> {
                            binding.progressBar.progress.visibility = View.GONE
                            mMovieAdapter.setMovies(it.data!!.results)
                        }
                        is Resource.Error -> {
                            binding.ivRefresh.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                        is Resource.Loading -> {
                            binding.ivRefresh.visibility = View.GONE
                            binding.progressBar.progress.visibility = View.VISIBLE
                        }
                    }
                })

            }

            MovieType.UPCOMING_MOVIE -> {
                binding.tvTitle.text = getString(R.string.upcoming_movie)
                movieListViewModel.fetchUpcommingMovie(apikey = BuildConfig.MOVIE_DB_KEY, page = 1)
                movieListViewModel.upCommingMovie.observe(viewLifecycleOwner, Observer {
                    when (it) {
                        is Resource.Success -> {
                            binding.progressBar.progress.visibility = View.GONE
                            mMovieAdapter.setMovies(it.data!!.results)
                        }
                        is Resource.Error -> {
                            binding.ivRefresh.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                        is Resource.Loading -> {
                            binding.ivRefresh.visibility = View.GONE
                            binding.progressBar.progress.visibility = View.VISIBLE
                        }
                    }
                })
            }
        }
    }
}