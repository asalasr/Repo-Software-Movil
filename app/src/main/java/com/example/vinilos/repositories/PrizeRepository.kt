package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.android.volley.Response
import com.example.vinilos.models.Prize
import org.json.JSONObject
import com.example.vinilos.network.VolleyBroker

class PrizeRepository (val application: Application){
    lateinit var volleyBroker: VolleyBroker
    fun postPrize(prize:Prize){

        val postParams = mapOf<String, Any>(
            "organization" to prize.organitation,
            "name" to prize.name,
            "description" to prize.description
        )

        volleyBroker = VolleyBroker(application.applicationContext)
        volleyBroker.instance.add(VolleyBroker.postRequest("prizes", JSONObject(postParams),
            Response.Listener<JSONObject> { response ->
                // Display the first 500 characters of the response string.
                Log.i("PrizeRepository" ,"Response is: ${response.toString()}")
            },
            Response.ErrorListener {
                Log.d("PrizeRepository", "error:"+it.toString()+" "+prize.organitation+" "+prize.description+" "+prize.name)

            }
        ))
    }

}