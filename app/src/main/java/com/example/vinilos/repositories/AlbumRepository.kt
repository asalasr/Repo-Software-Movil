package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinilos.models.Album
import com.example.vinilos.network.NetworkServiceAdapter

class AlbumRepository (val application: Application) {

    fun postAlbum(album: Album, cbSuccess: (resp: Boolean) -> Unit, cbError: (resp: VolleyError) -> Unit){

        NetworkServiceAdapter.getInstance(application).postAlbum(album,{
            Log.i("AlbumRepository" ,"Album Creado con Exito")
            cbSuccess(it)
        },{
            Log.i("AlbumRepository" ,"Error en la creacion")
            cbError(it)
        }
        )
    }

    fun getAlbums(cbSuccess: (resp: List<Album>) -> Unit, cbError: (resp: VolleyError) -> Unit){

        NetworkServiceAdapter.getInstance(application).getAlbums({
            Log.i("AlbumRepository" ,"Obtuvo álbums con extio")
            cbSuccess(it)
        },{
            Log.i("AlbumRepository" ,"Error en la obtención de álbums")
            cbError(it)
        }
        )
    }



}