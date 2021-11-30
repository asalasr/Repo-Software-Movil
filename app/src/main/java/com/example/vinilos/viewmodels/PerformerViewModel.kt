package com.example.vinilos.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vinilos.models.Performer
import com.example.vinilos.repositories.PerformerRepository

class PerformerViewModel (application: Application) : AndroidViewModel(application) {

    private var performerRepositoryObject = PerformerRepository(application)

    private val _performers = MutableLiveData<List<Performer>>()

    private var _Loanding = MutableLiveData<Boolean>(false)

    val performers: LiveData<List<Performer>>
        get() = _performers

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown



    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        Log.i("VieModelPermormer","llego aca en el refres data")
        performerRepositoryObject.refreshData( {
            _performers.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })

    }

    //onNetwork
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PerformerViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}