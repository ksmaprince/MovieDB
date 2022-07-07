package com.ksma.moviedb.data.model

data class UpComingMovie(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<MovieInfo>,
    val status_code: Int,
    val status_message: String
)