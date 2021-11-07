package com.example.vinilos.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.vinilos.R
import com.example.vinilos.viewmodels.AlbumViewModel
import java.util.*
import androidx.appcompat.app.AlertDialog
import com.example.vinilos.models.Album
import java.text.SimpleDateFormat


class FormAlbum : AppCompatActivity() {

    private lateinit var albumViewModelClass: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_album)
        val spinnerGender: Spinner = findViewById(R.id.spinner_genre)

        ArrayAdapter.createFromResource(
            this,
            R.array.genre_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerGender.adapter = adapter
        }

        val spinnerRecordLabel: Spinner = findViewById(R.id.spinner_record_label)

        ArrayAdapter.createFromResource(
            this,
            R.array.record_label_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRecordLabel.adapter = adapter
        }

        val releaseDatePicker : DatePicker = findViewById(R.id.datePickerReleaseDate)
        releaseDatePicker.maxDate=Date().getTime()

    }

    fun onCLickCreateAlbum(view: View) {

        val album:Album? = validateFormAlbum()

        if(album != null){
            Log.i("FormAlbum", "Click Button Crear")
            Toast.makeText(applicationContext,"Creando Album...",Toast.LENGTH_LONG).show()
            albumViewModelClass =  AlbumViewModel(this.application)

            albumViewModelClass.startPostCreate(album.name,album.cover, album.releaseDate,
                album.description, album.genre, album.recordLabel, {
                if (it){
                    showResponseAlert(R.string.albumCreationSuccess,android.R.drawable.ic_dialog_info,true)
                }
            }, {
                    showResponseAlert(R.string.albumCreationFail,android.R.drawable.ic_dialog_alert,false)
            } )
        } else {
            return
        }
    }

    fun showResponseAlert(message:Int,icon:Int,goBack:Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.album_alert_creation_title)
        builder.setMessage(message)
        builder.setIcon(icon)
        builder.setPositiveButton("OK") { dialogInterface, which ->
            if(goBack) {
                val intent = Intent(this, ListAlbums::class.java)
                startActivity(intent)
            }
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun validateFormAlbum (): Album? {

        val name : TextView = findViewById(R.id.editTextName)
        val cover : TextView = findViewById(R.id.editTextCover)
        val genre : Spinner = findViewById(R.id.spinner_genre)
        val recordLabel : Spinner = findViewById(R.id.spinner_record_label)
        val description : TextView = findViewById(R.id.editTextTextDescription)
        val releaseDatePicker : DatePicker = findViewById(R.id.datePickerReleaseDate)
        val releaseDate:Date = getReleaseDate(releaseDatePicker)

        var album:Album?= Album(name.text.toString(),cover.text.toString(),releaseDate,
            description.text.toString(),genre.selectedItem.toString(),recordLabel.selectedItem.toString())

        if (name.text.toString().trim().isEmpty()){
            name.setError(getString(R.string.campo_obligatorio))
            name.requestFocus()
            album= null
        }
        if (cover.text.toString().trim().isEmpty()){
            cover.setError(getString(R.string.campo_obligatorio))
            cover.requestFocus()
            album= null
        }

        if (genre.selectedItem.toString().equals(getString(R.string.album_spinner_text))){

            val spinnerError:TextView = genre.selectedView as TextView
            spinnerError.setError("");
            spinnerError.setTextColor(Color.RED);
            spinnerError.setText(getString(R.string.campo_obligatorio));
            album= null
        }
        if (recordLabel.selectedItem.toString().equals(getString(R.string.album_spinner_text))){
            val spinnerError:TextView = recordLabel.selectedView as TextView
            spinnerError.setError("");
            spinnerError.setTextColor(Color.RED);
            spinnerError.setText(getString(R.string.campo_obligatorio));
            album= null
        }
        if (description.text.toString().trim().isEmpty()){
            description.setError(getString(R.string.campo_obligatorio))
            description.requestFocus()
            album= null
        }

        return album

    }

    fun getReleaseDate(releaseDate : DatePicker):Date {

        val day: Int = releaseDate.getDayOfMonth()
        val month: Int = releaseDate.getMonth()
        val year: Int = releaseDate.getYear()
        val calendar = Calendar.getInstance()
        calendar[year, month] = day

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val formatedDate: String = sdf.format(calendar.time)

        return sdf.parse(formatedDate)
    }


}