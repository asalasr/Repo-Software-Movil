package com.example.vinilos.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.vinilos.R

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val bundle = intent.extras
        val dato = bundle?.get("role")
        Log.i("MainMenu", ""+dato)
        val titleMenu: String = getString(R.string.MainMenu)
        setTitle(titleMenu);
    }

    fun onClickMenuPrizes(view: View) {
        // Do something in response to button click

        Log.i("MainMenu", "Click Button Menu to prizes")
        val i = Intent(this, PrizesMenus::class.java)
        startActivity(i)
    }

    fun onClickMenuAlbums(view: View) {
        // Do something in response to button click

        Log.i("MainMenu", "Click Button Menu to albums")
        val i = Intent(this, ListAlbums::class.java)
        startActivity(i)
    }

    fun onClickMenuCollectors(view: View) {
        // Do something in response to button click

        Log.i("MainMenu", "Click Button Menu to Collectors")
        val i = Intent(this, CollectorMenu::class.java)
        startActivity(i)
    }
}