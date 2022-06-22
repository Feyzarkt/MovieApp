package com.feyzaurkut.movieapp.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favMovies", indices = [Index(value = ["id"], unique = true)])
data class MovieInfoEntity(
    var backdropPath: String? = null,
    var id: Int? = null,
    var genreIds: String,
    var originalLanguage: String? = null,
    var overview: String? = null,
    var popularity: Double? = null,
    var posterPath: String? = null,
    var releaseDate: String? = null,
    var title: String? = null,
    var voteAverage: Double? = null
) {
    @PrimaryKey(autoGenerate = true)
    var uniqueId: Int = 0
}