package com.example.vinilos.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.vinilos.repositories.PrizeRepository
import com.example.vinilos.models.Prize

class Prize constructor (val aplication: Application) {

  private lateinit var prizeRepositoryObject: PrizeRepository
    public fun startPostCreate( organitation:String,
                         name:String,
                         description:String) {
        val prize = Prize(organitation, name, description)
        Log.i("PrizeViewModel", "Se recibe: $organitation, $name y $description")

        prizeRepositoryObject = PrizeRepository(aplication)
        prizeRepositoryObject.postPrize(prize)
    }
}