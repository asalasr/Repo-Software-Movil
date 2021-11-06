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

    private val _prizes = MutableLiveData<List<Prize>>()

    //LIVE DATA
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    val loanding: LiveData<Boolean>
        get() = _Loanding

    val prizes: LiveData<List<Prize>>
        get() = _prizes

    //Repository
    private var prizeRepositoryObject = PrizeRepository(application)


    //Funciones

    init {
        refreshDataFromNetwork()
    }

    //post
    public fun startPostCreate( organitation:String,
                                name:String,
                                description:String,
                                cbViewSuccess: (resp:Boolean) -> Unit,
                                cbViewError: () -> Unit
    ) {
        val prize = Prize(0,organitation, name, description)
        Log.i("PrizeViewModel", "Se recibe: $organitation, $name y $description")
        _Loanding.value = true

        prizeRepositoryObject.postPrize(prize,{
            _Loanding.value = false
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
            cbViewSuccess(it)

        },{
            Log.d("Error", it.toString())
            _eventNetworkError.value = true
            _Loanding.value = false
            cbViewError()
        })
    }
    //onNetwork
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    //get all
    private fun refreshDataFromNetwork() {
        prizeRepositoryObject.refreshData({
            _prizes.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
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