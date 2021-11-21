package com.example.vinilos.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.vinilos.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onCLickSelectCollectorRol(view: View) {
        // Do something in response to button click

        Log.i("MainActivity", "Click Button Collector")
        val i = Intent(this, MainMenu::class.java)
        i.putExtra("role", "collector")
        startActivity(i)
    }

    fun onClickSelectVisitorRol(view: View) {
        // Do something in response to button click

        Log.i("MainActivity", "Click Button Visitor")
        val i = Intent(this, MainMenu::class.java)
        i.putExtra("role", "visitor")
        startActivity(i)
    }

}