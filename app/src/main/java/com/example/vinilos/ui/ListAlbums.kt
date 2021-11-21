package com.example.vinilos.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.vinilos.R

class ListAlbums : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_albums)
        val titleMenu: String = getString(R.string.albums)
        setTitle(titleMenu);
    }

    fun onClickFlapAlbums(view: View) {
        // Do something in response to button click

        Log.i("ListAlbums", "Click Button Add album")
        val i = Intent(this, FormAlbum::class.java)
        startActivity(i)

    }

}