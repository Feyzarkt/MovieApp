package com.feyzaurkut.movieapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.feyzaurkut.movieapp.data.model.MovieInfoEntity

@Database(entities = [MovieInfoEntity::class], version = 2)
abstract class FavMovieDatabase : RoomDatabase(){

    abstract fun favMovieDao(): FavMovieDao

    companion object {
        @Volatile private var instance: FavMovieDatabase? = null

        fun getDatabase(context: Context): FavMovieDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, FavMovieDatabase::class.java, "FavMovieDatabase")
                .fallbackToDestructiveMigration()
                .build()
    }
}