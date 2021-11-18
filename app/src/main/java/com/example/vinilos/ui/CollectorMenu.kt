package com.example.vinilos.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.R
import com.example.vinilos.models.Prize
import com.example.vinilos.repositories.CollectorRepository
import com.example.vinilos.viewmodels.CollectorViewModel
import com.example.vinilos.viewmodels.PrizeViewModel
import java.util.stream.Collector

class CollectorMenu : AppCompatActivity() {

    private lateinit var viewModel: CollectorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collector_menu)
        val titleMenu: String = getString(R.string.collectors)
        setTitle(titleMenu);


    }
}