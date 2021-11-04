package com.example.vinilos.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.vinilos.R

class PrizesMenus : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prizes_menus)

        Log.i("PrizesMenus", "onCreate ok")
        val titleMenu: String = getString(R.string.prizes)
        setTitle(titleMenu);

    }


    fun onClickFlapPrizes(view: View) {
        // Do something in response to button click

        Log.i("PrizesMenus", "Click Button Add prize")
        val i = Intent(this, FormPrizes::class.java)
        startActivity(i)

    }
}