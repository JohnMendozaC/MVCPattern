package com.lupesoft.mvcpattern.cart.controller.dimodule

import com.lupesoft.mvcpattern.movies.model.dataAccess.models.MovieModel
import com.lupesoft.mvcpattern.cart.model.models.ShoppingCartModel
import com.lupesoft.mvcpattern.movies.model.networking.daos.MovieDaoRetrofit
import com.lupesoft.mvcpattern.movies.model.repositories.MovieRepository
import com.lupesoft.mvcpattern.cart.model.repositories.ShoppingCartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class ShoppingModule {

    @Provides
    fun provideMovieModel(
        movieRepository: MovieRepository,
        movieDaoRetrofit: MovieDaoRetrofit
    ): MovieModel {
        return MovieModel(movieRepository, movieDaoRetrofit)
    }

    @Provides
    fun provideShoppingCartModel(shoppingCartRepository: ShoppingCartRepository): ShoppingCartModel {
        return ShoppingCartModel(shoppingCartRepository)
    }
}