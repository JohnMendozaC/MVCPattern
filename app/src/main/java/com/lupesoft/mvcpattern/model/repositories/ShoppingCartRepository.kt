package com.lupesoft.mvcpattern.model.repositories

import com.lupesoft.mvcpattern.model.vo.Movie

interface ShoppingCartRepository {

    fun getAllMoviesIntoShoppingCart(): List<Movie>

    fun addMovie(idMovie: Int): Long

    fun deleteMovie(idMovie: Int): Int

    fun deleteAllMovie(): Int
}