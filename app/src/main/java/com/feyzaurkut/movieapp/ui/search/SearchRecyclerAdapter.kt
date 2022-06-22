package com.feyzaurkut.movieapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.feyzaurkut.movieapp.R
import com.feyzaurkut.movieapp.data.model.Movie
import com.feyzaurkut.movieapp.databinding.ItemMovieBinding
import com.feyzaurkut.movieapp.util.*

class SearchRecyclerAdapter(
    private val movieList: ArrayList<Movie>,
    private val onClickListenerAdapter: OnClickListenerAdapter
) : ListAdapter<Movie, SearchRecyclerAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieBinding.bind(itemView)
        fun bind(movie: Movie) {
            with(binding) {
                movie.posterPath?.let { ivPoster.getPhoto(Constants.POSTER_URL + it) }
                tvTitle.text = movie.title
                cvMovie.setOnClickListener {
                    onClickListenerAdapter.onClick(adapterPosition)
                }
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

class DiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}