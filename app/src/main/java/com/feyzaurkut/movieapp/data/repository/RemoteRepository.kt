package com.feyzaurkut.movieapp.data.repository

import com.feyzaurkut.movieapp.data.network.MainService
import javax.inject.Inject

class RemoteRepository @Inject constructor( private val mainService: MainService) {

    suspend fun getMovies() = mainService.getMovies()
}