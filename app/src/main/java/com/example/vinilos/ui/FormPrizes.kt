package com.example.vinilos.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.vinilos.R
import com.example.vinilos.viewmodels.Prize

class FormPrizes : AppCompatActivity() {

    private lateinit var prizeViewModelClass: Prize

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_prizes)
        val titleMenu: String = getString(R.string.CreatePrizes)
        setTitle(titleMenu);
    }

    fun onCLickCreatePrize(view: View) {

        Log.i("FormPrizes", "Click Button Crear")

        prizeViewModelClass =  Prize(this.application)
        prizeViewModelClass.startPostCreate("organitation 1","name 1","description 1")

    }
}