package com.lupesoft.mvcpattern.movies.controller.dimodule

import com.lupesoft.mvcpattern.movies.model.repositories.MovieRepository
import com.lupesoft.mvcpattern.movies.model.dataAccess.repositories.MovieRepositoryRoom
import com.lupesoft.appshoppingcenter.infrastructure.dblocal.repositorys.ShoppingCartRepositoryRoom
import com.lupesoft.mvcpattern.cart.model.repositories.ShoppingCartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@InstallIn(FragmentComponent::class)
@Module
abstract class MoviesModule {

    @Binds
    abstract fun provideMovieRepository(movieRepositoryRoom: MovieRepositoryRoom): MovieRepository

    @Binds
    abstract fun provideShoppingCartRepository(shoppingCartRepositoryRoom: ShoppingCartRepositoryRoom): ShoppingCartRepository

}