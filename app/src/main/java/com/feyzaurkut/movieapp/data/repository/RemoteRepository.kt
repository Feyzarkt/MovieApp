package com.feyzaurkut.movieapp.data.repository

import com.feyzaurkut.movieapp.data.network.MainService
import javax.inject.Inject

class RemoteRepository @Inject constructor( private val mainService: MainService) {

    suspend fun getMovies() = mainService.getMovies()

    suspend fun searchMovie(query: String) = mainService.searchMovie(query)

    suspend fun getGenres() = mainService.getGenres()

    suspend fun getVideos(movieId: Int) = mainService.getVideos(movieId)
}