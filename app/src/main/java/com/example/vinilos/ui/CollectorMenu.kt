package com.example.vinilos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vinilos.R

class CollectorMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collector_menu)
        val titleMenu: String = getString(R.string.collectors)
        setTitle(titleMenu);
    }
}