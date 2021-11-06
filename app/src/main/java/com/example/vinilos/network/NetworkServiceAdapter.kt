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
import com.example.vinilos.models.Prize
import org.json.JSONArray
import org.json.JSONObject

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://vinyl-miso.herokuapp.com/"
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

    fun postPrize(prize: Prize, onComplete:(resp: Boolean)->Unit, onError: (error:VolleyError)->Unit) {
        val postParams = mapOf<String, Any>(
            "organization" to prize.organitation,
            "name" to prize.name,
            "description" to prize.description
        )

        requestQueue.add(postRequest("prizes",JSONObject(postParams),
            Response.Listener<JSONObject> { response ->

                var item:JSONObject? = null

                item = response
                Log.d("Response", item.toString())
                onComplete(true)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }


    fun getPrizes(onComplete:(resp:List<Prize>)->Unit, onError: (error:VolleyError)->Unit) {
        requestQueue.add(getRequest("prizes",
            Response.Listener<String> { response ->
                Log.d("tagb", response)
                val resp = JSONArray(response)
                val list = mutableListOf<Prize>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Prize(
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
            }))
    }


    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }
}