package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinilos.models.Performer
import com.example.vinilos.repositories.PerformerRepository

class PerformerViewModel (application: Application, artistId: Int) : AndroidViewModel(application) {

    private var performerRepositoryObject = PerformerRepository(application)

    private val _performers = MutableLiveData<List<Performer>>()

    private var _Loanding = MutableLiveData<Boolean>(false)

    val comments: LiveData<List<Performer>>
        get() = _performers

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id_artist: Int = artistId

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        performerRepositoryObject.refreshData(id_artist, {
            _performers.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })
    }

}