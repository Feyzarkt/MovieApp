package com.feyzaurkut.movieapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.feyzaurkut.movieapp.R
import com.feyzaurkut.movieapp.data.model.Movies
import com.feyzaurkut.movieapp.databinding.ItemMovieBinding
import com.feyzaurkut.movieapp.util.*

class HomeRecyclerAdapter (private val movieList: ArrayList<Movies>,
                           private val onClickListenerAdapter: OnClickListenerAdapter)
    : ListAdapter<Movies, HomeRecyclerAdapter.ViewHolder>(DiffCallback()){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemMovieBinding.bind(itemView)
        fun bind(movie: Movies){
            println(movie)
            movie.posterPath?.let { binding.ivPoster.getPhoto(Constants.IMAGES_URL+it) }
            binding.tvTitle.text = movie.title
            binding.cvMovie.setOnDoubleClickListener {
                onClickListenerAdapter.onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}

class DiffCallback : DiffUtil.ItemCallback<Movies>() {
    override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem == newItem
    }
}