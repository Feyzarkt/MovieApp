package com.feyzaurkut.movieapp.domain.usecase

import com.feyzaurkut.movieapp.data.model.RequestState
import com.feyzaurkut.movieapp.data.repository.LocalRepository
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetFavMoviesUseCase @Inject constructor(private val localRepository: LocalRepository) {

    suspend operator fun invoke() = flow {
        try {
            emit(RequestState.Loading())
            emit(RequestState.Success(localRepository.getFavMovies()))
        }catch (e: Exception){
            emit(RequestState.Error(e))
        }
    }
}