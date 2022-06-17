package com.feyzaurkut.movieapp.di

import android.content.Context
import com.feyzaurkut.movieapp.data.db.FavMovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) = FavMovieDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun injectDao(database: FavMovieDatabase) = database.favMovieDao()
}