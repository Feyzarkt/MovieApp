package com.feyzaurkut.movieapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
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
                    is RequestState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is RequestState.Success -> {
                        binding.progressBar.isVisible = false
                        initRecycler(requestState.data.movies)
                    }
                    is RequestState.Error -> {
                        binding.progressBar.isVisible = false
                    }
                }
            }
        }
    }

    private fun initRecycler(list: ArrayList<Movie>) {
        binding.rvMovies.apply {
            adapter = HomeRecyclerAdapter(list, object : OnClickListenerAdapter {
                override fun onClick(position: Int) {
                    val movie = DataMapper.mapMovieToMovieInfoEntities(list[position])
                    Log.e("click" , movie.toString())
                    val bundle = bundleOf("movieInfo" to list[position])
                    findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
                }
            })
        }
    }
}