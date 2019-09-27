package com.example.myapplication.di

import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface AppComponent {
    fun inject(myApplication: MyApplication)
}