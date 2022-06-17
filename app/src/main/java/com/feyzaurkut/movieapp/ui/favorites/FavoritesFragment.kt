package com.feyzaurkut.movieapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.feyzaurkut.movieapp.R
import com.feyzaurkut.movieapp.data.model.MovieInfoEntity
import com.feyzaurkut.movieapp.data.model.RequestState
import com.feyzaurkut.movieapp.databinding.FragmentFavoritesBinding
import com.feyzaurkut.movieapp.ui.viewmodel.LocalViewModel
import com.feyzaurkut.movieapp.util.DataMapper
import com.feyzaurkut.movieapp.util.OnClickListenerAdapter
import com.feyzaurkut.movieapp.util.RemoveAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val localViewModel: LocalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)

        initObserver()

        return binding.root
    }

    private fun initObserver() {
        localViewModel.getFavMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            localViewModel.favMoviesState.collect { requestState ->
                when (requestState) {
                    is RequestState.Success -> {
                        initRecycler(requestState.data)
                    }
                }
            }
        }
    }

    private fun initRecycler(list: List<MovieInfoEntity>) {
        binding.rvFavMovies.apply {
            adapter = FavoritesRecyclerAdapter(list, object : RemoveAdapter {
                override fun onClick(position: Int) {
                    list[position].id?.let { localViewModel.deleteFavMovie(it) }
                    initObserver()
                }
            }, object : OnClickListenerAdapter {
                override fun onClick(position: Int) {
                    val movie = DataMapper.mapMovieInfoEntitiesToMovie(list[position])
                    val bundle = bundleOf("movieInfo" to movie)
                    findNavController().navigate(
                        R.id.action_favoritesFragment_to_detailFragment,
                        bundle
                    )
                }
            })
        }
    }
}