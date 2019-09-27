package com.example.myapplication.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.common.Constants.Companion.PICTURE_PATH
import com.example.myapplication.common.inflate
import com.example.myapplication.common.loadUrl
import com.example.myapplication.models.MoviePopular
import com.example.myapplication.models.Results
import kotlinx.android.synthetic.main.row.view.*

class MovieAdaptor(private val moviePopular: MoviePopular/*arprivate val results: List<Results>*/,private val listener: OnMovieClickLister): RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(parent.inflate(R.layout.row,false))

    }

    override fun getItemCount(): Int {
      //  return List<Results>.results.size
        return moviePopular.results.size                                                        //[19404].id

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.title.text=moviePopular.results[position].title
        holder.image.loadUrl(PICTURE_PATH+moviePopular.results[position].poster_path)
        holder.bind(moviePopular.results[position],listener)

    }


}
class MovieViewHolder (view: View): RecyclerView.ViewHolder(view) {
    fun bind(results: Results,listener: OnMovieClickLister) {
        itemView.setOnClickListener {
            listener.onMovieClick(results)
        }
    }
    val title = view.tvTitle
    val image = view.image_id

}
interface OnMovieClickLister{

    fun onMovieClick(results: Results)
}
