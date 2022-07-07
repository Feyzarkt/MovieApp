package com.feyzaurkut.movieapp.data.network

import com.feyzaurkut.movieapp.data.model.GenresResponse
import com.feyzaurkut.movieapp.data.model.MoviesResponse
import com.feyzaurkut.movieapp.data.model.VideosResponse
import com.feyzaurkut.movieapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainService {

    @GET("trending/movie/day?api_key=${Constants.API_KEY}")
    //@GET("movie/popular?api_key=${Constants.API_KEY}")
    suspend fun getMovies(): MoviesResponse

    @GET("search/movie?api_key=${Constants.API_KEY}")
    suspend fun searchMovie(@Query("query") query: String?): MoviesResponse

    @GET("genre/movie/list?api_key=${Constants.API_KEY}")
    suspend fun getGenres(): GenresResponse

    @GET("movie/{movieId}/videos?api_key=${Constants.API_KEY}&language=en-US")
    suspend fun getVideos(@Path("movieId") movieId: Int): VideosResponse

}