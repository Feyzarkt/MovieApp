package com.feyzaurkut.movieapp.data.network

import com.feyzaurkut.movieapp.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MainService {

    @GET("movie/popular?api_key=3c7ab02722ce1ddde20129d8d03a8320")
    suspend fun getMovies(): MoviesResponse

}