package com.feyzaurkut.movieapp.domain.usecase

import com.feyzaurkut.movieapp.data.model.RequestState
import com.feyzaurkut.movieapp.data.repository.RemoteRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val remoteRepository: RemoteRepository) {

    suspend operator fun invoke() = flow {
        try {
            emit(RequestState.Loading())
            emit(RequestState.Success(remoteRepository.getMovies()))
        } catch (exception:Exception){
            emit(RequestState.Error(exception))
        }
    }

}