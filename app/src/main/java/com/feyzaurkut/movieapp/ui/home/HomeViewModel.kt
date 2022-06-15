package com.feyzaurkut.movieapp.ui.home

import androidx.lifecycle.ViewModel
import com.feyzaurkut.movieapp.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.feyzaurkut.movieapp.data.model.MoviesResponse
import com.feyzaurkut.movieapp.data.model.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase): ViewModel() {

    private val _moviesState = MutableStateFlow<RequestState<MoviesResponse>?>(null)
    val moviesState: StateFlow<RequestState<MoviesResponse>?> = _moviesState

    fun getMovies() = viewModelScope.launch {
        getMoviesUseCase.invoke().collect {
            _moviesState.value = it
        }
    }
}