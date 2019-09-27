package com.example.myapplication.viewmodel

import android.app.Application
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.common.Constants
import com.example.myapplication.models.MovieDatabase
import com.example.myapplication.models.MoviePopular
import com.example.myapplication.models.Results
import com.example.myapplication.network.MovieRequest
import com.example.myapplication.network.RetrofitInstance
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieViewModel @Inject constructor
    (private val movieRequest: MovieRequest, application: Application)
    :ViewModel(){
    private var movieList:  MutableLiveData<MoviePopular>? = MutableLiveData()
    private var movieListDB:  MutableLiveData<List<Results>>? = MutableLiveData()

    private var progressbarMutableData:  MutableLiveData<Boolean>? = MutableLiveData()
    private var errorMessagePage: MutableLiveData<Boolean>? = MutableLiveData()

    var showSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var showDbSuccess: MutableLiveData<Boolean> = MutableLiveData()



    var compositeDisposable = CompositeDisposable() //we can add several observable
    var resultsDao = MovieDatabase.getDatabase(application)?.resultsDAO()


    fun getMovieData(){

        progressbarMutableData?.value = true

      //  Log.i(TAG, "GET Movie result ")

        val call = movieRequest.getMoviePopular(Constants.API_KEY)

        compositeDisposable.add(

            call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({t -> handleResponse(t) })
               // .subscribe({t-> handleResponse, this::handleError)
        )
    }

fun getmovieList(): MutableLiveData<MoviePopular>{
    return movieList!!

}


    private fun handleResponse(result: MoviePopular) {

        movieList?.value = result
        addToDatababse(result )

        progressbarMutableData?.value = false
        errorMessagePage?.value = false


        showSuccess.value = true
    }

   fun addToDatababse(result: MoviePopular) {

        compositeDisposable.add(
            resultsDao!!.insertMovie(result.results)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({showDbSuccess.value=true},{
                    Log.i("viemodel_erroe",it.message!!)
                })
        )

    }

    fun getAllDBMovies(){

        compositeDisposable.add(
            resultsDao!!.getAllCakes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                            movie -> movieListDB!!.value = movie
                    },{}
                )
        )
    }

    fun returnDBResult(): MutableLiveData<List<Results>>?{
        return movieListDB
    }

    fun returnResult(): MutableLiveData<MoviePopular>?{
        progressbarMutableData?.value = true
        return movieList
    }

    fun returnProgressBar(): MutableLiveData<Boolean>?{
        return progressbarMutableData
    }


    private fun handleError(error: Throwable) {
        Log.d("Actors Error ", ""+error.message)
        errorMessagePage?.value = true
        progressbarMutableData?.value = false

        //telling the database is connection is success
        showSuccess.value = false

    }

    fun returnErrorResult(): MutableLiveData<Boolean>?{
        return errorMessagePage
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "ViewModel Destroyed")
    }


    companion object{
        const val TAG = "MovieViewModel"
    }


    fun onDestroy() {
        compositeDisposable.clear()
    }


}