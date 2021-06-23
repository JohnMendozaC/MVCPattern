package com.lupesoft.appshoppingcenter.infrastructure.dblocal.repositorys

import com.lupesoft.mvcpattern.model.repositories.MovieRepository
import com.lupesoft.mvcpattern.model.vo.Movie
import com.lupesoft.mvcpattern.model.dataAccess.daos.MovieDao
import com.lupesoft.mvcpattern.model.dataAccess.dtos.toDomainModel
import com.lupesoft.mvcpattern.model.dataAccess.entities.MovieEntity
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