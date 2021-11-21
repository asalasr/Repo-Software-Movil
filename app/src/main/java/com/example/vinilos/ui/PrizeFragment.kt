package com.example.vinilos.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.models.Prize
import androidx.lifecycle.Observer
import com.example.vinilos.databinding.FragmentPrizeBinding
import com.example.vinilos.ui.adapters.PrizeAdapter
import com.example.vinilos.viewmodels.PrizeViewModel


class PrizeFragment : Fragment() {
    private var _binding: FragmentPrizeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PrizeViewModel
    private var viewModelAdapter: PrizeAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrizeBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = PrizeAdapter()
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
        Log.i("titulo",""+activity.actionBar?.title)
        activity.actionBar?.title = getString(R.string.prizes)
        viewModel = ViewModelProvider(this, PrizeViewModel.Factory(activity.application)).get(
            PrizeViewModel::class.java)
        viewModel.prizes.observe(viewLifecycleOwner, Observer<List<Prize>> {
            it.apply {
                viewModelAdapter!!.prizes = this
                Log.i("llego aqui","hola soy api")
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}