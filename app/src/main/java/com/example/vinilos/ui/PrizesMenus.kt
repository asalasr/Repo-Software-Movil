package com.example.vinilos.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.vinilos.R
import com.example.vinilos.databinding.ActivityPrizesMenusBinding

class PrizesMenus : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPrizesMenusBinding.inflate(layoutInflater)
        Log.i("PrizesMenus", "onCreate ok")
        val titleMenu: String = getString(R.string.prizes)
        setTitle(titleMenu);
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Instantiate the navController using the NavHostFragment
        navController = navHostFragment.navController
        Log.d("act", navController.toString())
        setupActionBarWithNavController(navController)



}
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun onClickFlapPrizes(view: View) {
        // Do something in response to button click

        Log.i("PrizesMenus", "Click Button Add prize")
        val i = Intent(this, FormPrizes::class.java)
        startActivity(i)

    }
}