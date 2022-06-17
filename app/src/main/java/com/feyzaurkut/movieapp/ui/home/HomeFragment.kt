package com.feyzaurkut.movieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.feyzaurkut.movieapp.R
import com.feyzaurkut.movieapp.data.model.Movie
import com.feyzaurkut.movieapp.data.model.RequestState
import com.feyzaurkut.movieapp.databinding.FragmentHomeBinding
import com.feyzaurkut.movieapp.ui.viewmodel.LocalViewModel
import com.feyzaurkut.movieapp.ui.viewmodel.RemoteViewModel
import com.feyzaurkut.movieapp.util.DataMapper
import com.feyzaurkut.movieapp.util.OnClickListenerAdapter
import com.feyzaurkut.movieapp.util.OnDoubleClickListenerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val remoteViewModel: RemoteViewModel by viewModels()
    private val localViewModel: LocalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromApi()
    }

    private fun getDataFromApi() {
        remoteViewModel.getMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            remoteViewModel.moviesState.collect { requestState ->
                when (requestState) {
                    is RequestState.Success -> {
                        initRecycler(requestState.data.movies)
                    }
                }
            }
        }
    }

    private fun initRecycler(list: ArrayList<Movie>) {
        binding.rvMovies.apply {
            adapter = HomeRecyclerAdapter(list, object : OnDoubleClickListenerAdapter {
                override fun onClick(position: Int) {
                    val favMovieInfo = DataMapper.mapMovieToMovieInfoEntities(list[position])
                    localViewModel.insertFavMovie(favMovieInfo)
                    Toast.makeText(
                        requireContext(),
                        "${favMovieInfo.title} added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, object : OnClickListenerAdapter {
                override fun onClick(position: Int) {
                    val bundle = bundleOf("movieInfo" to list[position])
                    findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
                }
            })
        }
    }
}