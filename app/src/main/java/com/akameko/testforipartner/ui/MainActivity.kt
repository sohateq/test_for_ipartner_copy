package com.akameko.testforipartner.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.akameko.testforipartner.R

/**
 * This is MainActivity. Provides navigation between fragments.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment_container)
        setupActionBarWithNavController(navController, AppBarConfiguration(navController.graph))
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}