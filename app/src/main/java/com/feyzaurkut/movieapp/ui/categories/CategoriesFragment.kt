package com.feyzaurkut.movieapp.ui.categories

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.feyzaurkut.movieapp.R
import com.feyzaurkut.movieapp.databinding.FragmentCategoriesBinding


class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoriesBinding.inflate(inflater)

        initListView()
        initListeners()

        return binding.root
    }

    private fun initListView() {
        val categories = listOf("Action-Adventure", "Animation", "Classic", "Comedy", "Drama",
            "Horror", "Family", "Mystery", "Scifi-Fantasy", "Western")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories)

        binding.lvCategories.adapter = adapter
    }

    private fun initListeners() {
        binding.lvCategories.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->

            val selectedItem = parent.getItemAtPosition(position) as String

            Toast.makeText(requireContext(), "Seçilen kategori: $selectedItem", Toast.LENGTH_SHORT).show()
        }
    }
}