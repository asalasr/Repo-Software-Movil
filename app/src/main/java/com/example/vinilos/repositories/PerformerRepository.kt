package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinilos.models.Performer
import com.example.vinilos.network.NetworkServiceAdapter

class PerformerRepository (val application: Application) {

    fun refreshData(
        callback: (List<Performer>) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        Log.i("VieModelPermormer","llego aca super bien network")
        NetworkServiceAdapter.getInstance(application).getPerformerDetail(
            {
                //Guardar los coleccionistas de la variable it en un almacén de datos local para uso futuro
                callback(it)
            },
            onError
        )
    }
}