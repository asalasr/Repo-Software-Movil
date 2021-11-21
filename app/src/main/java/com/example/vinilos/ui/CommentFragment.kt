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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.FragmentCommentBinding
import com.example.vinilos.databinding.FragmentPrizeBinding
import com.example.vinilos.models.Comment
import com.example.vinilos.models.Prize
import com.example.vinilos.ui.adapters.CommentAdapter
import com.example.vinilos.ui.adapters.PrizeAdapter
import com.example.vinilos.viewmodels.CommentViewModel
import com.example.vinilos.viewmodels.PrizeViewModel


class CommentFragment : Fragment() {

    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CommentViewModel
    private var viewModelAdapter: CommentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = CommentAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.fragmentsRvcomments
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
        //val args: CommentFragmentArgs by navArgs()
        //Log.d("ArgsCommebt", args.albumId.toString())
        viewModel = ViewModelProvider(this, CommentViewModel.Factory(activity.application,100)).get(
            CommentViewModel::class.java)
        viewModel.comments.observe(viewLifecycleOwner, Observer<List<Comment>> {
            it.apply {
                viewModelAdapter!!.comments = this
                Log.i("llego aqui","hola soy api comments")
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