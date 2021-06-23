package com.lupesoft.mvcpattern.model.repositories

import com.lupesoft.mvcpattern.model.vo.Movie
import com.lupesoft.mvcpattern.model.dataAccess.entities.MovieEntity

interface MovieRepository {

    fun getAllMovies(): List<Movie>

    fun insertAll(entities: List<MovieEntity>)
}