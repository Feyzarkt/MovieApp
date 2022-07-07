package com.feyzaurkut.movieapp.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.feyzaurkut.movieapp.R
import com.feyzaurkut.movieapp.data.model.Video
import com.feyzaurkut.movieapp.databinding.ItemVideosBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VideosRecyclerAdapter(
    private val videosList: ArrayList<Video>,
) : ListAdapter<Video, VideosRecyclerAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemVideosBinding.bind(itemView)
        fun bind(video: Video) {
            with(binding) {
                youtubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        video.key?.let { youTubePlayer.cueVideo(it, 0f) }
                        clipProgressBar.isVisible = false
                    }
                })
                tvVideoTitle.text = video.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_videos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videosList[position])
    }

    override fun getItemCount(): Int {
        return videosList.size
    }

}

class DiffCallback : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }
}