package com.feyzaurkut.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class VideosResponse(

    var id: Int? = null,
    @SerializedName("results")
    var videos: ArrayList<Video> = arrayListOf()

)