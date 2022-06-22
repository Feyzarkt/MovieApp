package com.feyzaurkut.movieapp.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.feyzaurkut.movieapp.data.model.Movie
import com.feyzaurkut.movieapp.data.model.RequestState
import com.feyzaurkut.movieapp.databinding.FragmentDetailBinding
import com.feyzaurkut.movieapp.ui.viewmodel.LocalViewModel
import com.feyzaurkut.movieapp.ui.viewmodel.RemoteViewModel
import com.feyzaurkut.movieapp.util.Constants
import com.feyzaurkut.movieapp.util.DataMapper
import com.feyzaurkut.movieapp.util.getPhoto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val movieInfo: Movie by lazy { arguments?.getParcelable<Movie>("movieInfo") as Movie}
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

        return binding.root
    }

    private fun initUI(genres: String) {
        with(binding) {
            collapsingToolBar.title = movieInfo.title
            ivBackdrop.getPhoto(Constants.BACKDROP_URL+movieInfo.backdropPath)
            ivPoster.getPhoto(Constants.POSTER_URL+movieInfo.posterPath)
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
            remoteViewModel.genresState.collect { requestState ->
                when (requestState) {
                    is RequestState.Success -> {
                        requestState.data.genres.forEach { genre->
                            movieInfo.genreIds.forEach { id->
                                if (id == genre.id){
                                    genre.name?.let { genres.add(it) }
                                }
                            }
                        }
                        initUI(genres.joinToString (", "))
                    }
                }
            }
        }
    }

    private fun initListener(){
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

    private fun observeSearchedFavMovie(){
        movieInfo.id?.let { localViewModel.searchFavMovie(it) }
        viewLifecycleOwner.lifecycleScope.launch {
            localViewModel.searchedMovieState.collect { requestState->
                when(requestState) {
                    is RequestState.Success -> {
                        if(requestState.data != null) {
                            isFavorite = true
                        }
                    }
                }
            }
        }
    }
}