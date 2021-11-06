package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.android.volley.Response
import com.example.vinilos.models.Album
import com.example.vinilos.models.Prize
import com.example.vinilos.network.VolleyBroker
import org.json.JSONObject

class AlbumRepository (val application: Application) {

    lateinit var volleyBroker: VolleyBroker
    fun postAlbum(album: Album, cbVM: (resp: Int) -> Unit){

        val postParams = mapOf<String, Any>(
            "name" to album.name,
            "cover" to album.cover,
            "releaseDate" to album.releaseDate,
            "description" to album.description,
            "genre" to album.genre,
            "recordLabel" to album.recordLabel
        )

        volleyBroker = VolleyBroker(application.applicationContext)
        volleyBroker.instance.add(
            VolleyBroker.postRequest("albums", JSONObject(postParams),
            Response.Listener<JSONObject> { response ->
                Log.i("AlbumRepository" ,"Response is: ${response.toString()}")
                // Creacion correcta
                cbVM(0)
            },
            Response.ErrorListener {
                Log.d("AlbumRepository", "error")
                // Error al crear
                cbVM(-1)
            }
        ))
    }
}