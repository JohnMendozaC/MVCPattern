package com.lupesoft.mvcpattern.controller.dimodule

import com.lupesoft.mvcpattern.model.repositories.MovieRepository
import com.lupesoft.appshoppingcenter.infrastructure.dblocal.repositorys.MovieRepositoryRoom
import com.lupesoft.appshoppingcenter.infrastructure.dblocal.repositorys.ShoppingCartRepositoryRoom
import com.lupesoft.mvcpattern.model.repositories.ShoppingCartRepository
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