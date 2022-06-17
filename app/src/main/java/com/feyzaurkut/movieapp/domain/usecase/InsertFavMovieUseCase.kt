package com.feyzaurkut.movieapp.domain.usecase

import com.feyzaurkut.movieapp.data.model.MovieInfoEntity
import com.feyzaurkut.movieapp.data.model.RequestState
import com.feyzaurkut.movieapp.data.repository.LocalRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertFavMovieUseCase @Inject constructor(private val localRepository: LocalRepository) {

    suspend operator fun invoke(favMovieEntity: MovieInfoEntity) = flow {
        try {
            emit(RequestState.Loading())
            emit(RequestState.Success(localRepository.insertFavMovie(favMovieEntity)))
        } catch (exception:Exception){
            emit(RequestState.Error(exception))
        }
    }

}