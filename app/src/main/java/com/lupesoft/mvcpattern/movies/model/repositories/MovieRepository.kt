package com.lupesoft.mvcpattern.movies.model.repositories

import com.lupesoft.mvcpattern.movies.model.dataAccess.vo.Movie
import com.lupesoft.mvcpattern.movies.model.dataAccess.entities.MovieEntity

interface MovieRepository {

    fun getAllMovies(): List<Movie>

    fun insertAll(entities: List<MovieEntity>)
}