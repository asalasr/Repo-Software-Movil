package com.example.vinilos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.vinilos.R

class ListArtist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_artist)
        val titleMenu: String = getString(R.string.artists)
        setTitle(titleMenu);

    }
}