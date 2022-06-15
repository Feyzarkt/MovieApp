package com.feyzaurkut.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var movies: ArrayList<Movies> = arrayListOf()
)