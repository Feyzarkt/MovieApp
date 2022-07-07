package com.feyzaurkut.movieapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.feyzaurkut.movieapp.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.feyzaurkut.movieapp.data.model.GenresResponse
import com.feyzaurkut.movieapp.data.model.MoviesResponse
import com.feyzaurkut.movieapp.data.model.RequestState
import com.feyzaurkut.movieapp.data.model.VideosResponse
import com.feyzaurkut.movieapp.domain.usecase.GetGenresUseCase
import com.feyzaurkut.movieapp.domain.usecase.GetVideosUseCase
import com.feyzaurkut.movieapp.domain.usecase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RemoteViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val searchMovieUseCase: SearchMovieUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getVideosUseCase: GetVideosUseCase
) : ViewModel() {

    private val _moviesState = MutableStateFlow<RequestState<MoviesResponse>?>(null)
    val moviesState: StateFlow<RequestState<MoviesResponse>?> = _moviesState

    private val _searchState = MutableStateFlow<RequestState<MoviesResponse>?>(null)
    val searchState: StateFlow<RequestState<MoviesResponse>?> = _searchState

    private val _genresState = MutableStateFlow<RequestState<GenresResponse>?>(null)
    val genresState: StateFlow<RequestState<GenresResponse>?> = _genresState

    private val _videosState = MutableStateFlow<RequestState<VideosResponse>?>(null)
    val videosState: StateFlow<RequestState<VideosResponse>?> = _videosState

    fun getMovies() = viewModelScope.launch {
        getMoviesUseCase.invoke().collect {
            _moviesState.value = it
        }
    }

    fun searchMovie(query: String) = viewModelScope.launch {
        searchMovieUseCase.invoke(query).collect {
            _searchState.value = it
        }
    }

    fun getGenres() = viewModelScope.launch {
        getGenresUseCase.invoke().collect {
            _genresState.value = it
        }
    }

    fun getVideos(movieId: Int) = viewModelScope.launch {
        getVideosUseCase.invoke(movieId).collect {
            _videosState.value = it
        }
    }
}