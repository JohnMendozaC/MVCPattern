package com.lupesoft.mvcpattern.controller.dimodule

import com.lupesoft.mvcpattern.model.models.MovieModel
import com.lupesoft.mvcpattern.model.models.ShoppingCartModel
import com.lupesoft.mvcpattern.model.networking.daos.MovieDaoRetrofit
import com.lupesoft.mvcpattern.model.repositories.MovieRepository
import com.lupesoft.mvcpattern.model.repositories.ShoppingCartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent

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