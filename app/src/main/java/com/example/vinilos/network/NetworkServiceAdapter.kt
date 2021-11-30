package com.example.vinilos.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinilos.models.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat

class NetworkServiceAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "https://vinyl-miso.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }


    /*fun getOneAlbum(albumId:Int, onComplete:(resp: Album)->Unit, onError: (error:VolleyError)->Unit) {
        requestQueue.add(getRequest("albums/$albumId",
            Response.Listener<String> { response ->
                val resp = JSONObject(response)
                var item:JSONObject? = null

                item = resp
                Log.d("Response", item.toString())
                onComplete(Album(id= item.getInt("id"),
                    name = item.getString("name"),
                    cover = item.getString("cover"),
                    releaseDate = item.getString("releaseDate"),
                    description = item.getString("description"),
                    genre = item.getString("genre"),
                    recordLabel = item.getString("recordLabel")))
            },
            Response.ErrorListener {
                onError(it)
            }))
    }*/

    fun postPrize(
        prize: Prize,
        onComplete: (resp: Boolean) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        val postParams = mapOf<String, Any>(
            "organization" to prize.organitation,
            "name" to prize.name,
            "description" to prize.description
        )

        requestQueue.add(
            postRequest("prizes", JSONObject(postParams),
                Response.Listener<JSONObject> { response ->

                    var item: JSONObject? = null

                    item = response
                    Log.d("Response", item.toString())
                    onComplete(true)
                },
                Response.ErrorListener {
                    onError(it)
                })
        )
    }


    fun getPrizes(onComplete: (resp: List<Prize>) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(
            getRequest("prizes",
                Response.Listener<String> { response ->
                    Log.d("tagb", response)
                    val resp = JSONArray(response)
                    val list = mutableListOf<Prize>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            i, Prize(
                                id = item.getInt("id"),
                                name = item.getString("name"),
                                organitation = item.getString("organization"),
                                description = item.getString("description")
                            )
                        )
                    }
                    onComplete(list)
                },
                Response.ErrorListener {
                    onError(it)
                    Log.d("Error get Prizes", it.message.toString())
                })
        )
    }




    fun postAlbum(
        album: Album,
        onComplete: (resp: Boolean) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        val postParams = mapOf<String, Any>(
            "name" to album.name,
            "cover" to album.cover,
            "releaseDate" to album.releaseDate,
            "description" to album.description,
            "genre" to album.genre,
            "recordLabel" to album.recordLabel
        )

        requestQueue.add(
            postRequest("albums", JSONObject(postParams),
                Response.Listener<JSONObject> { response ->

                    var item: JSONObject? = null

                    item = response
                    Log.d("Response", item.toString())
                    onComplete(true)
                },
                Response.ErrorListener {
                    onError(it)
                })
        )
    }

    fun getCollectors(
        onComplete: (resp: List<Collector>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        Log.d("NetworkServices", "llego a collector")
        requestQueue.add(
            getRequest("collectors",
                Response.Listener<String> { response ->
                    Log.d("tagb", response)
                    val resp = JSONArray(response)
                    val list = mutableListOf<Collector>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        val idsAlbum = item.getJSONArray("collectorAlbums")
                        val albumsIds: MutableList<Int> = ArrayList()
                        for (j in 0 until idsAlbum.length()) {
                            albumsIds.add(j,idsAlbum.getJSONObject(j).getString("id").toInt())
                        }
                        list.add(
                            i, Collector(
                                name = item.getString("name"),
                                email = item.getString("email"),
                                telephone = item.getString("telephone"),
                                albumsIds.toTypedArray()
                            )

                        )
                    }
                    onComplete(list)
                },
                Response.ErrorListener {
                    onError(it)
                    Log.d("Error get Collectors", it.message.toString())
                })
        )
    }

    fun getCommentsAlbum(
        albumId: Int,
        onComplete: (resp: List<Comment>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        Log.d("NetworkServices", "llego a Comment")
        requestQueue.add(
            getRequest("albums/$albumId/comments",
                Response.Listener<String> { response ->
                    Log.d("tagb", response)
                    val resp = JSONArray(response)
                    val list = mutableListOf<Comment>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            i, Comment(
                                description = item.getString("description"),
                                rating = item.getInt("rating"),
                                id = item.getInt("id")
                            )
                        )
                    }
                    onComplete(list)
                },
                Response.ErrorListener {
                    onError(it)
                    Log.d("Error get Comments", it.message.toString())
                })
        )
    }

    fun postComment(
        albumId: Int,
        comment: CommentCollector,
        onComplete: (resp: Boolean) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {

        val collector = mapOf<String, Any>(
            "id" to comment.collector.id
        )

        val postParams = mapOf<String, Any>(
            "description" to comment.description,
            "rating" to comment.rating,
            "collector" to collector
        )

        val obj = JSONObject(postParams)
        Log.d("salida", obj.toString())

        requestQueue.add(
            postRequest("albums/$albumId/comments", obj,
                Response.Listener<JSONObject> { response ->

                    var item: JSONObject? = null

                    item = response
                    Log.d("Response", item.toString())
                    onComplete(true)
                },
                Response.ErrorListener {
                    onError(it)
                })
        )
    }

    fun getAlbums(onComplete: (resp: List<Album>) -> Unit, onError: (error: VolleyError) -> Unit) {
        requestQueue.add(
            getRequest("albums",
                { response ->
                    Log.d("tagb", response)
                    val resp = JSONArray(response)
                    val list = mutableListOf<Album>()
                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            i, Album(
                                name = item.getString("name"),
                                cover = item.getString("cover"),
                                description = item.getString("description"),
                                recordLabel = item.getString("recordLabel"),
                                genre = item.getString("genre"),
                                releaseDate = SimpleDateFormat("dd-MM-yyyy").parse(item.getString("releaseDate")),
                                id = item.getString("id")?.toInt()
                            )
                        )
                    }
                    onComplete(list)
                },
                {
                    onError(it)
                    Log.d("Error get Prizes", it.message.toString())
                })
        )
    }

    fun getPerformerDetail(
        onComplete: (resp: List<Performer>) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        requestQueue.add(
            getRequest("musicians",
                Response.Listener<String> { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Performer>()

                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)
                        list.add(
                            i, Performer(
                                id = item.getInt("id"),
                                name = item.getString("name"),
                                image = item.getString("image"),
                                description = item.getString("description"),
                                createDate = item.getString("birthDate")

                            )
                        )
                    }
                    onComplete(list)
                },
                Response.ErrorListener {
                    Log.d("Error Performerdetail", it.message.toString())
                    onError(it)
                })
        )
    }
    fun getAlbum(
        albumId: Int,
        onComplete: (resp: Album) -> Unit,
        onError: (error: VolleyError) -> Unit
    ) {
        Log.d("NetworkServices", "obtener álbum "+albumId)
        requestQueue.add(
            getRequest("albums/$albumId",
                { response ->
                    Log.d("álbum", response)
                    val item = JSONObject(response)
                    onComplete( Album(
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        description = item.getString("description"),
                        recordLabel = item.getString("recordLabel"),
                        genre = item.getString("genre"),
                        releaseDate = SimpleDateFormat("dd-MM-yyyy").parse(item.getString("releaseDate")),
                        id = item.getString("id")?.toInt()
                    ))
                },
                {
                    onError(it)
                    Log.d("Error obteniendo álbum "+albumId, it.message.toString())
                })
        )
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    private fun postRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.POST,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }

    private fun putRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.PUT,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }


}