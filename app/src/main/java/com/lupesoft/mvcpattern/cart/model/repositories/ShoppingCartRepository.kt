package com.lupesoft.mvcpattern.cart.model.repositories

import com.lupesoft.mvcpattern.movies.model.dataAccess.vo.Movie

interface ShoppingCartRepository {

    fun getAllMoviesIntoShoppingCart(): List<Movie>

    fun addMovie(idMovie: Int): Long

    fun deleteMovie(idMovie: Int): Int

    fun deleteAllMovie(): Int
}