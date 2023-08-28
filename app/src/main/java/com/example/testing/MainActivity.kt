package com.example.testing

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.testing.auth.AuthActivity
import com.example.testing.databinding.ActivityMainBinding
import com.example.testing.databinding.NavigationHeaderBinding
import com.example.testing.home.HomeFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var headerBinding: NavigationHeaderBinding
    lateinit var navController: NavController
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        headerBinding = NavigationHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        firebaseAuth = FirebaseAuth.getInstance()

        actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.nav_open, R.string.nav_close)

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        val headerView = layoutInflater.inflate(R.layout.navigation_header, binding.navigationDrawer, false)
        actionBarDrawerToggle.syncState()
        binding.navigationDrawer.addHeaderView(headerView)

        headerBinding.userImageNavigationDrawer.setImageResource(R.drawable.image)
        headerBinding.userNameNavigationDrawer.text = "Rameez"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

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

        binding.navigationDrawer.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.myAccount -> {
                    Toast.makeText(this, "Account Clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.favourites -> {
                    Toast.makeText(this, "Favourites Clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.trash -> {
                    Toast.makeText(this, "Trash Clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.settings -> {
                    Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.logout -> {
                    clearUserCredentials()
                    firebaseAuth.signOut()
                    clearCachedGoogleSignInCredentials()
                    startActivity(Intent(this, AuthActivity::class.java))
                    finish()
                    Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> {
                    true
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        } else {
            return super.onOptionsItemSelected(item)

        }
    }


    private fun showCreateDialog(context: Context) {
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

    private fun clearUserCredentials() {
        val sharedPreferences = getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("username")
        editor.remove("password")
        editor.apply()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val currentFragment = supportFragmentManager.findFragmentById(R.id.home)
        Log.d("TAG", "current Fragment: " + currentFragment)
        if (currentFragment !is HomeFragment) {
            Log.d("TAG", "onBackPressed: ")
            binding.bottomNavigationView.selectedItemId = R.id.home

        }
    }

    fun clearCachedGoogleSignInCredentials() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .build()

        val googleSignInClient = GoogleSignIn.getClient(binding.root.context, gso)
        googleSignInClient.signOut()
        googleSignInClient.revokeAccess()
    }

//    interface onBackPressedCallback {
//        fun onBackPressed()
//    }
    override fun onDestroy() {
        super.onDestroy()
        binding == null
    }

}