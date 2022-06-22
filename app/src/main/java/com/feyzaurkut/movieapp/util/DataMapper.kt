package com.feyzaurkut.movieapp.util


import com.feyzaurkut.movieapp.data.model.Movie
import com.feyzaurkut.movieapp.data.model.MovieInfoEntity

object DataMapper {
    fun mapMovieInfoEntitiesToMovie(input: MovieInfoEntity): Movie {
            return Movie(
                adult = null,
                backdropPath = input.backdropPath,
                genreIds = Converters.fromString(input.genreIds),
                id = input.id,
                originalLanguage = input.originalLanguage,
                originalTitle = null,
                overview = input.overview,
                popularity = input.popularity,
                posterPath = input.posterPath,
                releaseDate = input.releaseDate,
                title = input.title,
                video = null,
                voteAverage = input.voteAverage,
                voteCount = null
            )
    }

    fun mapMovieToMovieInfoEntities(input: Movie): MovieInfoEntity {
            return MovieInfoEntity(
                backdropPath = input.backdropPath,
                id = input.id,
                genreIds = Converters.fromArrayList(input.genreIds),
                originalLanguage = input.originalLanguage,
                overview = input.overview,
                popularity = input.popularity,
                posterPath = input.posterPath,
                releaseDate = input.releaseDate,
                title = input.title,
                voteAverage = input.voteAverage,
            )
    }
}