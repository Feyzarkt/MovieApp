package com.feyzaurkut.movieapp.domain.usecase

import com.feyzaurkut.movieapp.data.model.RequestState
import com.feyzaurkut.movieapp.data.repository.RemoteRepository
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(private val remoteRepository: RemoteRepository) {

    suspend fun invoke(movieId: Int) = flow {
        try {
            emit(RequestState.Loading())
            emit(RequestState.Success(remoteRepository.getVideos(movieId)))
        } catch (e: Exception) {
            emit(RequestState.Error(e))
        }
    }
}