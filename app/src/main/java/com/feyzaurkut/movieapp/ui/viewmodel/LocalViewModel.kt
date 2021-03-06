package com.feyzaurkut.movieapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feyzaurkut.movieapp.data.model.MovieInfoEntity
import com.feyzaurkut.movieapp.data.model.RequestState
import com.feyzaurkut.movieapp.domain.usecase.DeleteFavMovieUseCase
import com.feyzaurkut.movieapp.domain.usecase.GetFavMoviesUseCase
import com.feyzaurkut.movieapp.domain.usecase.InsertFavMovieUseCase
import com.feyzaurkut.movieapp.domain.usecase.SearchFavMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalViewModel @Inject constructor(
    private val insertFavMovieUseCase: InsertFavMovieUseCase,
    private val getFavMoviesUseCase: GetFavMoviesUseCase,
    private val deleteFavMovieUseCase: DeleteFavMovieUseCase,
    private val searchFavMovieUseCase: SearchFavMovieUseCase
) : ViewModel() {

    private val _favMoviesState = MutableStateFlow<RequestState<List<MovieInfoEntity>>?>(null)
    val favMoviesState: StateFlow<RequestState<List<MovieInfoEntity>>?> = _favMoviesState

    private val _searchedMovieState = MutableStateFlow<RequestState<MovieInfoEntity?>?>(null)
    val searchedMovieState: StateFlow<RequestState<MovieInfoEntity?>?> = _searchedMovieState

    fun insertFavMovie(favMovieEntity: MovieInfoEntity) = viewModelScope.launch {
        insertFavMovieUseCase.invoke(favMovieEntity).collect {
        }
    }

    fun deleteFavMovie(id: Int) = viewModelScope.launch {
        deleteFavMovieUseCase.invoke(id).collect {
        }
    }

    fun getFavMovies() = viewModelScope.launch {
        getFavMoviesUseCase.invoke().collect {
            _favMoviesState.value = it
        }
    }

    fun searchFavMovie(id:Int) = viewModelScope.launch {
        searchFavMovieUseCase.invoke(id).collect {
            _searchedMovieState.value = it
        }
    }
}