package com.example.testing

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testing.databinding.ActivityMainBinding
import com.example.testing.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

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
                R.id.homeFragment, R.id.createPostFragment, R.id.searchFragment, R.id.profileFragment, R.id.createStoryFragment
            )
        )

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homeFragment)
                }

                R.id.createPost -> {
                    showCreateDialog(this)
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

//        binding.bottomNavigationView.selectedItemId = R.id.home

//        setupActionBarWithNavController(navController, appBarConfiguration)

    }


    fun showCreateDialog(context: Context) {
        val options = arrayOf("Create Story", "Create Post")

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose Creation")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> {
                    navController.navigate(R.id.createStoryFragment)
                }
                1 -> {
                    navController.navigate(R.id.createPostFragment)
                }
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onBackPressed() {

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)

        if(currentFragment !is HomeFragment) {
//            navController.navigate(R.id.homeFragment)
            binding.bottomNavigationView.selectedItemId = R.id.home
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding == null
    }

}