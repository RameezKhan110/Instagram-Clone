package com.example.testing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testing.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var  navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        val appBarConfiguration = AppBarConfiguration(
            setOf
                (
                R.id.homeFragment
                ,R.id.searchFragment
                ,R.id.profileFragment
            ))

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener ()
        {
            when(it.itemId)
            {
                R.id.home ->
                {
                    navController.navigate(R.id.homeFragment)
                }
                R.id.search -> {
                    navController.navigate(R.id.searchFragment)
                }

                R.id.profile -> {
                    navController.navigate(R.id.profileFragment)
                }
                else -> {

                }

            }

            return@setOnItemSelectedListener true
        }
        setupActionBarWithNavController(navController,appBarConfiguration)

    }

}