package com.example.vk_api.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.vk_api.R
import com.example.vk_api.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnDataPass {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragment.navController
        navController.graph.setStartDestination(R.id.homeFragment)
        initBottomNavBar()
        initToolBar()
        initHideToolAndBottomBarListener()
    }

    private fun initHideToolAndBottomBarListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.authFragment) {
                binding.mainToolBar.visibility = View.GONE
                binding.mainBnv.visibility = View.GONE
            } else {
                binding.mainBnv.visibility = View.VISIBLE
                binding.mainToolBar.visibility = View.VISIBLE
            }
        }
    }

    private fun initToolBar() {
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.mainToolBar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(binding.mainToolBar)
    }

    private fun initBottomNavBar() {
        val bottomNavView = binding.mainBnv
        NavigationUI.setupWithNavController(bottomNavView, navController)
        bottomNavView.setupWithNavController(navController)
    }

    override fun menuTitle(data: String) {
        supportActionBar?.title = data
    }
}