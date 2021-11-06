package com.example.vinilos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.vinilos.R
import com.example.vinilos.viewmodels.AlbumViewModel
import com.example.vinilos.viewmodels.Prize
import java.util.*
import android.widget.Toast

class FormAlbum : AppCompatActivity() {

    private lateinit var albumViewModelClass: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_album)
        val titleMenu: String = getString(R.string.CreateAlbums)
        setTitle(titleMenu);
    }

    fun onCLickCreateAlbum(view: View) {

        Log.i("FormAlbum", "Click Button Crear")
        Toast.makeText(applicationContext,"Creando Album...",Toast.LENGTH_LONG).show()
        albumViewModelClass =  AlbumViewModel(this.application)
        albumViewModelClass.startPostCreate("Album Test","Cover Test", Date(),"Description test", "Classical", "EMI" ) {
            if (it == 0){
                Toast.makeText(applicationContext,"Album creado correctamente",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext,"Error, Intente de nuevo",Toast.LENGTH_LONG).show()
            }
        }


    }
}