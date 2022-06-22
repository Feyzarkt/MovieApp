package com.feyzaurkut.movieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.feyzaurkut.movieapp.R
import com.feyzaurkut.movieapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private  lateinit var navController: NavController
    private  lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNav= binding.bottomNavigation

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)

        setNavigation()
        setBottomNavVisibility()
    }

    private fun setBottomNavVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> hideBottomNav()
                R.id.detailFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }
    }

    private fun setNavigation() {
        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.favoritesFragment -> {
                    navController.navigate(R.id.favoritesFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    navController.navigate(R.id.searchFragment)
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
    }

    private fun hideBottomNav(){
        bottomNav.isVisible = false
    }

    private fun showBottomNav(){
        bottomNav.isVisible = true
    }
}