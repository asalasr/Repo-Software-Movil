package com.example.vinilos.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vinilos.models.Comment
import com.example.vinilos.models.CommentCollector
import com.example.vinilos.repositories.CommentRepository

class CommentViewModel(application: Application, albumId: Int) : AndroidViewModel(application) {

    private var commentsRepositoryObject = CommentRepository(application)

    private val _comments = MutableLiveData<List<Comment>>()

    private var _Loanding = MutableLiveData<Boolean>(false)

    val comments: LiveData<List<Comment>>
        get() = _comments

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id_album: Int = albumId

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        commentsRepositoryObject.refreshData(id_album, {
            _comments.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })
    }

    //post
    fun startPostCreate(
        comment: CommentCollector,
        cbViewSuccess: (resp: Boolean) -> Unit,
        cbViewError: () -> Unit
    ) {

        Log.i(
            "CommentViewModel",
            "Se recibe: rating: ${comment.rating}, comment: ${comment.description} albumId: $id_album collector:${comment.collector.id}"
        )
        _Loanding.value = true

        commentsRepositoryObject.postCommentAlbum(id_album, comment, {
            _Loanding.value = false
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
            cbViewSuccess(it)

        }, {
            Log.d("Error", it.toString())
            _eventNetworkError.value = true
            _Loanding.value = false
            cbViewError()
        })
    }


    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CommentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CommentViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}