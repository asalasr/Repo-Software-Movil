package com.example.vinilos.viewmodels

import android.app.Application
import android.text.style.BulletSpan
import android.util.Log
import com.example.vinilos.models.Album
import com.example.vinilos.models.Prize
import com.example.vinilos.repositories.AlbumRepository
import java.util.*

class AlbumViewModel (val aplication: Application) {

    private lateinit var albumRepositoryObject: AlbumRepository
    fun startPostCreate(name:String,
                               cover:String,
                               releaseDate: Date,
                               description: String,
                               genre: String,
                               recordLabel: String,
                               cbViewSuccess: (resp:Boolean) -> Unit,
                               cbViewError: () -> Unit
                               ) {
        val album = Album(name=name, cover=cover, releaseDate=releaseDate,
            description=description, genre=genre, recordLabel=recordLabel,id=null)
        Log.i("AlbumViewModel", "name: $name")
        Log.i("AlbumViewModel", "cover: $cover")
        Log.i("AlbumViewModel", "releaseDate: $releaseDate")
        Log.i("AlbumViewModel", "description: $description")
        Log.i("AlbumViewModel", "genre: $genre")
        Log.i("AlbumViewModel", "recordLabel: $recordLabel")


        albumRepositoryObject = AlbumRepository(aplication)
        albumRepositoryObject.postAlbum(album,{
            Log.i("AlbumViewModel", "Return from Repository: $it")
            cbViewSuccess(it)
        },{
            Log.i("AlbumViewModel", "Error at Repository")
            cbViewError()
        })
    }

    fun startGetAlbums(        cbViewSuccess: (resp:List<Album>) -> Unit,
                               cbViewError: () -> Unit
    ) {

        albumRepositoryObject = AlbumRepository(aplication)

        albumRepositoryObject.getAlbums({
            Log.i("AlbumViewModel", "Return from Repository: $it")
            cbViewSuccess(it)
        },{
            Log.i("AlbumViewModel", "Error at Repository")
            cbViewError()
        })
    }



}