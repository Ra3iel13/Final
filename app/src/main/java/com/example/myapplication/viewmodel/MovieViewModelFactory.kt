package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.network.MovieRequest

class MovieViewModelFactory (private val movieRequest: MovieRequest, private val application: Application)
    : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(movieRequest, application) as T
    }

}