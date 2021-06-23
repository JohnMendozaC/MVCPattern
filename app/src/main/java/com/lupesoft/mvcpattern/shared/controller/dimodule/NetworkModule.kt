package com.lupesoft.mvcpattern.shared.controller.dimodule

import com.lupesoft.mvcpattern.movies.model.networking.Api
import com.lupesoft.mvcpattern.movies.model.networking.daos.MovieDaoRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class NetworkModule {
    @Provides
    fun provideMovieDaoRetrofit(): MovieDaoRetrofit = Api.create()
}