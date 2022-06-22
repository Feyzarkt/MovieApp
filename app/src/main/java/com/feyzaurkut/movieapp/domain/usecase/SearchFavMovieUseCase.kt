package com.feyzaurkut.movieapp.domain.usecase

import com.feyzaurkut.movieapp.data.model.RequestState
import com.feyzaurkut.movieapp.data.repository.LocalRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchFavMovieUseCase @Inject constructor(private val localRepository: LocalRepository) {

    suspend operator fun invoke(id: Int) = flow {
        try {
            emit(RequestState.Loading())
            emit(RequestState.Success(localRepository.searchFavMovie(id)))
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }
}