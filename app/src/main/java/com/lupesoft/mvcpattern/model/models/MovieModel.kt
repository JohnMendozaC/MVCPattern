package com.lupesoft.mvcpattern.model.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.lupesoft.mvcpattern.model.repositories.MovieRepository
import com.lupesoft.mvcpattern.model.dataAccess.utils.response.Resource
import com.lupesoft.mvcpattern.model.networking.daos.MovieDaoRetrofit
import com.lupesoft.mvcpattern.model.networking.dtos.toMovie
import com.lupesoft.mvcpattern.model.networking.dtos.toMovieEntity
import com.lupesoft.mvcpattern.model.networking.response.ApiEmptyResponse
import com.lupesoft.mvcpattern.model.networking.response.ApiErrorResponse
import com.lupesoft.mvcpattern.model.networking.response.ApiResponse
import com.lupesoft.mvcpattern.model.networking.response.ApiSuccessResponse
import com.lupesoft.mvcpattern.model.vo.Movie
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FragmentScoped
class MovieModel @Inject constructor(
    private var movieRepository: MovieRepository,
    private var movieDaoRetrofit: MovieDaoRetrofit
) {

    private fun getAllMoviesFromNetwork(): Flow<Resource<List<Movie>>> {
        val flow = flow {
            val response = movieDaoRetrofit.getAllMovies()
            when (val apiResponse = ApiResponse.create(response)) {
                is ApiSuccessResponse -> {
                    emit(Resource.loading(null, null))
                    movieRepository.insertAll(apiResponse.body.items.toMovieEntity())
                    emit(Resource.success(apiResponse.body.items.toMovie()))
                }
                is ApiEmptyResponse -> {
                    emit(
                        Resource.error(
                            null,
                            0,
                            null
                        )
                    )
                }
                is ApiErrorResponse -> {
                    emit(Resource.error(null, apiResponse.code, apiResponse.message))
                }
            }
        }

        return flow
            .onStart { emit(Resource.loading(null, null)) }
            .catch { exception ->
                with(exception) {
                    emit(Resource.error(null, 0, message))
                }
            }
            .flowOn(Dispatchers.IO)
    }

    fun getAllMovies(): LiveData<Resource<List<Movie>>> {
        val flow = flow {
            movieRepository.getAllMovies().let {
                if (it.isNotEmpty()) {
                    emit(Resource.success(it))
                } else {
                    getAllMoviesFromNetwork().collect { service ->
                        emit(service)
                    }
                }
            }
        }
        return flow
            .onStart { emit(Resource.loading(null, null)) }
            .catch { exception ->
                emit(
                    Resource.error(
                        null,
                        0,
                        exception.message
                    )
                )
            }
            .flowOn(Dispatchers.IO)
            .asLiveData()
    }

}