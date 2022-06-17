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

    /*@Query("SELECT* FROM hotspots WHERE apiKey=:apiKey AND serialNumber=:serialNum")
    suspend fun searchHotspot(apiKey: String, serialNum: String): List<HotspotInfo>

    @Query("SELECT * FROM hotspots ORDER BY id DESC LIMIT 1")
    suspend fun getLastHotspot(): HotspotInfo*/
}