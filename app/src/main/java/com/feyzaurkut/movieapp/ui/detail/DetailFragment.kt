package com.feyzaurkut.movieapp.ui.detail

import android.R
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.feyzaurkut.movieapp.data.model.Movie
import com.feyzaurkut.movieapp.data.model.RequestState
import com.feyzaurkut.movieapp.data.model.Video
import com.feyzaurkut.movieapp.databinding.FragmentDetailBinding
import com.feyzaurkut.movieapp.ui.home.HomeRecyclerAdapter
import com.feyzaurkut.movieapp.ui.viewmodel.LocalViewModel
import com.feyzaurkut.movieapp.ui.viewmodel.RemoteViewModel
import com.feyzaurkut.movieapp.util.Constants
import com.feyzaurkut.movieapp.util.DataMapper
import com.feyzaurkut.movieapp.util.OnClickListenerAdapter
import com.feyzaurkut.movieapp.util.getPhoto
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val movieInfo: Movie by lazy { arguments?.getParcelable<Movie>("movieInfo") as Movie }
    private val remoteViewModel: RemoteViewModel by viewModels()
    private val localViewModel: LocalViewModel by viewModels()
    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)

        getGenresFromApi()
        initListener()
        observeSearchedFavMovie()
        getVideos()

        return binding.root
    }

    private fun initUI(genres: String) {
        with(binding) {
            collapsingToolBar.title = movieInfo.title
            ivBackdrop.getPhoto(Constants.BACKDROP_URL + movieInfo.backdropPath)
            ivPoster.getPhoto(Constants.POSTER_URL + movieInfo.posterPath)
            tvGenres.text = genres
            tvReleaseDate.text = "( " + movieInfo.releaseDate + " )"
            tvVoteAverage.text = movieInfo.voteAverage.toString()
            tvLanguage.text = movieInfo.originalLanguage?.uppercase()
            tvOverview.text = movieInfo.overview

            if (isFavorite) btnFav.setColorFilter(Color.RED)
            else btnFav.setColorFilter(Color.WHITE)
        }
    }

    private fun getGenresFromApi() {
        val genres = arrayListOf<String>()
        remoteViewModel.getGenres()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                remoteViewModel.genresState.collect { requestState ->
                    when (requestState) {
                        is RequestState.Success -> {
                            requestState.data.genres.forEach { genre ->
                                movieInfo.genreIds.forEach { id ->
                                    if (id == genre.id) {
                                        genre.name?.let { genres.add(it) }
                                    }
                                }
                            }
                            initUI(genres.joinToString(", "))
                        }
                    }
                }
            }
        }
    }

    private fun initListener() {
        binding.btnFav.setOnClickListener {
            isFavorite = if (isFavorite) {
                Log.e("if", isFavorite.toString())
                movieInfo.id?.let { it1 -> localViewModel.deleteFavMovie(it1) }
                binding.btnFav.setColorFilter(Color.WHITE)
                false
            } else {
                Log.e("else", isFavorite.toString())
                val favMovieInfo = DataMapper.mapMovieToMovieInfoEntities(movieInfo)
                localViewModel.insertFavMovie(favMovieInfo)
                binding.btnFav.setColorFilter(Color.RED)
                true
            }
        }
    }

    private fun observeSearchedFavMovie() {
        movieInfo.id?.let { localViewModel.searchFavMovie(it) }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                localViewModel.searchedMovieState.collect { requestState ->
                    when (requestState) {
                        is RequestState.Success -> {
                            if (requestState.data != null) {
                                isFavorite = true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getVideos() {
        movieInfo.id?.let { remoteViewModel.getVideos(it) }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                remoteViewModel.videosState.collect { requestState ->
                    when (requestState) {
                        is RequestState.Loading -> {
                            binding.trailerProgressBar.isVisible = true
                        }
                        is RequestState.Success -> {
                            requestState.data.videos.forEach { video ->
                                if (video.type == "Trailer") {
                                    initYoutubePlayer(video.key.toString())
                                }
                            }
                            initRecycler(requestState.data.videos)
                        }
                        is RequestState.Error -> {
                            binding.trailerProgressBar.isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun initYoutubePlayer(trailerKey: String) {
        lifecycle.addObserver(binding.youtubePlayerTrailer)
        binding.youtubePlayerTrailer.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(trailerKey, 0f)
                binding.trailerProgressBar.isVisible = false
            }
        })
    }

    private fun initRecycler(list: ArrayList<Video>) {
        binding.rvVideos.apply {
            adapter = VideosRecyclerAdapter(list)
        }
    }
}