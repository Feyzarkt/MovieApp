package com.feyzaurkut.movieapp.data.repository

import com.feyzaurkut.movieapp.data.db.FavMovieDao
import com.feyzaurkut.movieapp.data.model.MovieInfoEntity
import javax.inject.Inject


class LocalRepository @Inject constructor(private val favMovieDao: FavMovieDao) {

    suspend fun insertFavMovie(favMovieEntity: MovieInfoEntity) = favMovieDao.insertFavMovie(favMovieEntity)

    suspend fun deleteFavMovie(id: Int) = favMovieDao.deleteFavMovie(id)

    suspend fun getFavMovies() = favMovieDao.getFavMovies()

    suspend fun searchFavMovie(id: Int) = favMovieDao.searchFavMovie(id)
}