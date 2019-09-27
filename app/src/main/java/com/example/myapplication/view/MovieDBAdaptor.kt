package com.example.myapplication.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.common.Constants
import com.example.myapplication.common.inflate
import com.example.myapplication.common.loadUrl
import com.example.myapplication.models.MoviePopular
import com.example.myapplication.models.Results
import kotlinx.android.synthetic.main.rawdb.view.*
import kotlinx.android.synthetic.main.row.view.*

class MovieDBAdaptor(private val results: List<Results>, private val listener: OnMovieDBClickLister): RecyclerView.Adapter<MovieDBViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDBViewHolder {
        return MovieDBViewHolder(parent.inflate(R.layout.rawdb, false))

    }

    override fun getItemCount(): Int {

        return results.size                                                        //[19404].id

    }

    override fun onBindViewHolder(holder: MovieDBViewHolder, position: Int) {
        holder.title.text=results[position].title
        holder.image.loadUrl(Constants.PICTURE_PATH +results[position].poster_path)
        holder.bind(results[position],listener)

    }


}
class MovieDBViewHolder (view: View): RecyclerView.ViewHolder(view) {
    fun bind(results: Results, listener: OnMovieDBClickLister) {
        itemView.setOnClickListener {
            listener.onMovieClick(results)
        }
    }
    val title = view.tvTitle_DB
    val image = view.image_id_DB

}
interface OnMovieDBClickLister{

    fun onMovieClick(results: Results)
}
