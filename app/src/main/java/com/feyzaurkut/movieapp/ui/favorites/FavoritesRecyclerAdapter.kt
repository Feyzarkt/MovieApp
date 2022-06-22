package com.feyzaurkut.movieapp.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.feyzaurkut.movieapp.R
import com.feyzaurkut.movieapp.data.model.MovieInfoEntity
import com.feyzaurkut.movieapp.databinding.ItemFavMovieBinding
import com.feyzaurkut.movieapp.util.Constants
import com.feyzaurkut.movieapp.util.OnClickListenerAdapter
import com.feyzaurkut.movieapp.util.OnClickListenerRemoveAdapter
import com.feyzaurkut.movieapp.util.getPhoto

class FavoritesRecyclerAdapter(
    private val favMovieEntityList: List<MovieInfoEntity>,
    private val onClickListenerRemoveAdapter: OnClickListenerRemoveAdapter,
    private val onClickListenerAdapter: OnClickListenerAdapter
) : ListAdapter<MovieInfoEntity, FavoritesRecyclerAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFavMovieBinding.bind(itemView)
        fun bind(movieEntity: MovieInfoEntity) {
            with(binding) {
                movieEntity.posterPath?.let { ivPoster.getPhoto(Constants.POSTER_URL + it) }
                tvTitle.text = movieEntity.title
                ivDelete.setOnClickListener {
                    onClickListenerRemoveAdapter.onClick(adapterPosition)
                }
                cvMovie.setOnClickListener {
                    onClickListenerAdapter.onClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_fav_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favMovieEntityList[position])

    }

    override fun getItemCount(): Int {
        return favMovieEntityList.size
    }

}

class DiffCallback : DiffUtil.ItemCallback<MovieInfoEntity>() {
    override fun areItemsTheSame(oldItem: MovieInfoEntity, newItem: MovieInfoEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieInfoEntity, newItem: MovieInfoEntity): Boolean {
        return oldItem == newItem
    }
}