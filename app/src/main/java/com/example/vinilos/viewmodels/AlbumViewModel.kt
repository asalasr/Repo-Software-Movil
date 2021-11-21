package com.example.vinilos.viewmodels

import android.app.Application
import android.text.style.BulletSpan
import android.util.Log
import androidx.lifecycle.*
import com.example.vinilos.models.Album
import com.example.vinilos.models.Collector
import com.example.vinilos.models.Prize
import com.example.vinilos.repositories.AlbumRepository
import com.example.vinilos.repositories.CollectorRepository
import java.util.*

class AlbumViewModel (val aplication: Application) :  AndroidViewModel(aplication) {

    private var albumRepositoryObject = AlbumRepository(aplication)
    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    private val _albums = MutableLiveData<List<Album>>()

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    val albums: LiveData<List<Album>>
        get() = _albums


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

        albumRepositoryObject.postAlbum(album,{
            Log.i("AlbumViewModel", "Return from Repository: $it")
            cbViewSuccess(it)
        },{
            Log.i("AlbumViewModel", "Error at Repository")
            cbViewError()
        })
    }

    //Funciones
    init {
        refreshDataFromNetwork()
    }

    //onNetwork
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    //get all
    private fun refreshDataFromNetwork() {

        Log.i("ListCollector", "llego a consultar collector")
        albumRepositoryObject.getAlbums({
            _albums.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    //CLASS FACTORY
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}