package com.example.vinilos.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vinilos.models.Prize
import com.example.vinilos.repositories.PrizeRepository

class PrizeViewModel (application: Application) :  AndroidViewModel(application) {


    //MUTABLES
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    private var _Loanding = MutableLiveData<Boolean>(false)

    //LIVE DATA
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    val loanding: LiveData<Boolean>
        get() = _Loanding


    //Repository
    private var prizeRepositoryObject = PrizeRepository(application)


    //Funciones
    public fun startPostCreate( organitation:String,
                                name:String,
                                description:String
    ) {
        val prize = Prize(0,organitation, name, description)
        Log.i("PrizeViewModel", "Se recibe: $organitation, $name y $description")
        _Loanding.value = true

        prizeRepositoryObject.postPrize(prize,{
            _Loanding.value = false
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            Log.d("Error", it.toString())
            _eventNetworkError.value = true
            _Loanding.value = false
        })
    }
    //CLASS FACTORY
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PrizeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PrizeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}