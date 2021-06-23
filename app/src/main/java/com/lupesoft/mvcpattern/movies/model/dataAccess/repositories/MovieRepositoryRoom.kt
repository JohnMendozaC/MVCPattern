package com.lupesoft.mvcpattern.movies.model.dataAccess.repositories

import com.lupesoft.mvcpattern.movies.model.repositories.MovieRepository
import com.lupesoft.mvcpattern.movies.model.dataAccess.vo.Movie
import com.lupesoft.mvcpattern.movies.model.dataAccess.dao.MovieDao
import com.lupesoft.mvcpattern.cart.model.dataAccess.dtos.toDomainModel
import com.lupesoft.mvcpattern.movies.model.dataAccess.entities.MovieEntity
import javax.inject.Inject

class MovieRepositoryRoom @Inject constructor(
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getAllMovies(): List<Movie> {
        return movieDao.getAllMovies().toDomainModel()
    }

    override fun insertAll(entities: List<MovieEntity>) {
        return movieDao.insertAll(entities)
    }
}