package com.example.vinilos.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.FragmentAlbumBinding
import com.example.vinilos.databinding.FragmentArtistBinding
import com.example.vinilos.databinding.FragmentListArtistBinding
import com.example.vinilos.models.Album
import com.example.vinilos.models.Performer
import com.example.vinilos.ui.adapters.AlbumAdapter
import com.example.vinilos.ui.adapters.ArtistAdapter
import com.example.vinilos.viewmodels.AlbumViewModel
import com.example.vinilos.viewmodels.PerformerViewModel


class ListArtistFragment : Fragment() {

    private var _binding: FragmentListArtistBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PerformerViewModel
    private var viewModelAdapter: ArtistAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListArtistBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = ArtistAdapter()

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.fragmentsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        Log.i("titulo", "" + activity.actionBar?.title)

        viewModel = ViewModelProvider(this, PerformerViewModel.Factory(activity.application)).get(
            PerformerViewModel::class.java
        )
        viewModel.performers.observe(viewLifecycleOwner, Observer<List<Performer>> {
            it.apply {
                viewModelAdapter!!.performers = this
            }
        })
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

}