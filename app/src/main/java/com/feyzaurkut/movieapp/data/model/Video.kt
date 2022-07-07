package com.feyzaurkut.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Video(

    @SerializedName("iso_639_1")
    var iso6391: String? = null,
    @SerializedName("iso_3166_1")
    var iso31661: String? = null,
    var name: String? = null,
    var key: String? = null,
    var site: String? = null,
    var size: Int? = null,
    var type: String? = null,
    var official: Boolean? = null,
    @SerializedName("published_at")
    var publishedAt: String? = null,
    var id: String? = null

)