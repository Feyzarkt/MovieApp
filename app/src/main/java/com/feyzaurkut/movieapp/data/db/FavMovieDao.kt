package com.feyzaurkut.movieapp.data.db

import androidx.room.*
import com.feyzaurkut.movieapp.data.model.MovieInfoEntity

@Dao
interface FavMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMovie(movieInfoEntity: MovieInfoEntity)

    @Query("DELETE FROM favMovies WHERE id=:id")
    suspend fun deleteFavMovie(id: Int)

    @Query("SELECT* FROM favMovies")
    suspend fun getFavMovies(): List<MovieInfoEntity>

    @Query("SELECT* FROM favMovies WHERE id=:id")
    suspend fun searchFavMovie(id: Int): MovieInfoEntity?
}