package com.example.vinilos.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.vinilos.R
import com.example.vinilos.models.Comment
import com.example.vinilos.viewmodels.CommentViewModel


class ArtistDetailFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_detail, container, false)



    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }

        val rooView = view
        val frv = rooView!!.findViewById<View>(R.id.texto1) as TextView


        val args: ArtistDetailFragmentArgs by navArgs()
        if(args.artista == null)
            frv.setText("sin artista")

        frv.setText(args.artista.name)
    }


}