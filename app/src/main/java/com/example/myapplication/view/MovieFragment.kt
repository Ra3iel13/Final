package com.example.myapplication.view


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.myapplication.R
import com.example.myapplication.di.DaggerMovieComponent
import com.example.myapplication.di.MovieModule
import com.example.myapplication.di.NetworkModule
import com.example.myapplication.models.MoviePopular
import com.example.myapplication.models.Results
import com.example.myapplication.viewmodel.MovieViewModel
import com.example.myapplication.viewmodel.MovieViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.error_include
import kotlinx.android.synthetic.main.noswnce.*
import javax.inject.Inject


class MovieFragment : Fragment() {
    companion object {
        fun newInstance() = MovieFragment()
    }

    @Inject
    lateinit var movieViewModelFactory: MovieViewModelFactory

    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DaggerMovieComponent.builder()
            .networkModule(NetworkModule(activity!!.application))
            .movieModule(MovieModule())
            .build()
            .inject(this)

        viewModel =
            ViewModelProviders.of(this, movieViewModelFactory).get(MovieViewModel::class.java)

        val movieDB: MutableLiveData<List<Results>>? = viewModel.returnDBResult()

        viewModel.getMovieData()

        val movieTitle: MutableLiveData<MoviePopular> = viewModel.getmovieList()


        movieTitle.observe(this, object : Observer<MoviePopular> {
            override fun onChanged(t: MoviePopular?) {
                Log.i("MovieTitle", "${t!!.results[0].title}")

                val adaptor: MovieAdaptor = MovieAdaptor(t!!, object : OnMovieClickLister {
                    override fun onMovieClick(results: Results) {

                    }

                })
                rv_list_frg.layoutManager = LinearLayoutManager(activity)
                rv_list_frg.adapter = adaptor

            }
        })




        viewModel.getAllDBMovies()

        movieDB?.observe(this, object: Observer<List<Results>>{
            override fun onChanged(t: List<Results>?) {
                Log.i("MovieFrag", t!![0].original_title)
                val adaptor=MovieDBAdaptor(
                    t!!, object: OnMovieDBClickLister {
                        override fun onMovieClick(results: Results) {

                        }

                    })
               rv_list_frg_DB.layoutManager=LinearLayoutManager(activity)
                rv_list_frg_DB.adapter = adaptor
            }

        })//end of db call





        viewModel.returnProgressBar()?.observe(this, object : Observer<Boolean> {
            override fun onChanged(t: Boolean?) {
                if (t == true) {
                    prgs_bar.visibility = View.VISIBLE
                } else {
                    prgs_bar.visibility = View.GONE
                }
            }
        })

        viewModel.returnErrorResult()?.observe(this, object : Observer<Boolean> {
            override fun onChanged(t: Boolean?) {

                if (t == true) {
                    Toast.makeText(activity, "Show error page", Toast.LENGTH_SHORT).show()
                    error_include.visibility = View.VISIBLE
                } else {
                    error_include.visibility = View.GONE
                }
            }

        })

        viewModel.showDbSuccess.observe(this, Observer {
            if (it == true) {
                Toast.makeText(context, "got Movie Database successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, "Something went wrong with db", Toast.LENGTH_SHORT).show()
            }
        })





        return inflater.inflate(R.layout.fragment_movie, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}
