package com.feyzaurkut.movieapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.feyzaurkut.movieapp.data.model.Movies
import com.feyzaurkut.movieapp.data.model.RequestState
import com.feyzaurkut.movieapp.databinding.FragmentHomeBinding
import com.feyzaurkut.movieapp.util.DoubleClickListener
import com.feyzaurkut.movieapp.util.OnClickListenerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        getDataFromApi()

        return binding.root
    }

    private fun getDataFromApi() {
        viewModel.getMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.moviesState.collect { requestState ->
                when (requestState) {
                    is RequestState.Success -> {
                        initRecycler(requestState.data.movies)
                    }
                }
            }
        }
    }

    private fun initRecycler(list: ArrayList<Movies>) {
        binding.rvMovies.apply {
            adapter = HomeRecyclerAdapter(list, object : OnClickListenerAdapter {
                override fun onClick(position: Int) {
                    //Fav a ekle
                    Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}