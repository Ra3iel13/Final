package com.example.myapplication.di

import com.example.myapplication.view.MovieFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(MovieModule::class, NetworkModule::class))
interface MovieComponent {
    fun inject(movieFragment: MovieFragment)
}