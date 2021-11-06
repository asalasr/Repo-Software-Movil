package com.example.vinilos.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.R
import com.example.vinilos.viewmodels.PrizeViewModel
import androidx.lifecycle.Observer

class FormPrizes : AppCompatActivity() {

    private lateinit var prizeViewModelClass: PrizeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_prizes)
        val titleMenu: String = getString(R.string.CreatePrizes)
        setTitle(titleMenu);
        val activity = requireNotNull(this) {
            "You can only access the viewModel after onActivityCreated()"
        }
       // Iniciar el view model
        prizeViewModelClass = ViewModelProvider(this, PrizeViewModel.Factory(activity.application)).get(PrizeViewModel::class.java)
       //iniciar el lugar de observar
        val getResultTextView : TextView = findViewById(R.id.textviewTest)
        val getResultErrorViewText : TextView = findViewById(R.id.textviewTestError)
        val getResultLoadText : TextView = findViewById(R.id.textviewLoad)

        //Iniciar los observer
        val eventNetworkErrorObserver = Observer<Boolean> { newName ->
            // Update the UI, in this case, a TextView.
            getResultErrorViewText.text = "eventNetworkErrorObserver: "+newName
        }

        val isNetworkErrorShownObserver = Observer<Boolean> { newName ->
            // Update the UI, in this case, a TextView.
            getResultTextView.text = "isNetworkErrorShownObserver: "+newName
        }
        val isLoandingObserver = Observer<Boolean> { newName ->
            // Update the UI, in this case, a TextView.
            getResultLoadText.text = "isNetworkErrorShownObserver: "+newName
        }

        //vincular los observer
        prizeViewModelClass.isNetworkErrorShown.observe(this, isNetworkErrorShownObserver)
        prizeViewModelClass.eventNetworkError.observe(this, eventNetworkErrorObserver)
        prizeViewModelClass.loanding.observe(this, isLoandingObserver)

    }

    fun onCLickCreatePrize(view: View) {

        Log.i("FormPrizes", "Click Button Crear")

        prizeViewModelClass.startPostCreate("organitation otro","name otro","description otro")

    }

    fun onCLickCancelPrize(view: View) {

        Log.i("FormPrizes", "Click Button Cancelar")

    }
}