package com.example.myapplication.di

import android.app.Application
import com.example.myapplication.network.MovieRequest
import com.example.myapplication.viewmodel.MovieViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MovieModule {
    @Provides
    @Singleton
    fun provideCakeViewModelFactory(movieRequest: MovieRequest, application: Application): MovieViewModelFactory {
        return MovieViewModelFactory(movieRequest, application)
    }

}