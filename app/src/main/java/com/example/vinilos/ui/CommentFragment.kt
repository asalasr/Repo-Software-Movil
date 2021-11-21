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
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.FragmentCommentBinding
import com.example.vinilos.models.Comment
import com.example.vinilos.ui.adapters.CommentAdapter
import com.example.vinilos.viewmodels.CommentViewModel








class CommentFragment : Fragment() {
    var myButton: Button? = null
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

        val rooView = view
        val frv = rooView!!.findViewById<View>(R.id.detailAlbumtext) as TextView

        Log.i("titulo",""+activity.actionBar?.title)
        activity.actionBar?.title = getString(R.string.prizes)
        val args: CommentFragmentArgs  by navArgs()
        Log.d("ArgsCommebt", args.albumId.toString())
        Log.d("ArgsCommebt", args.descripcion.toString())
        frv.setText(args.descripcion.toString())

        myButton = rooView!!.findViewById<View>(R.id.button_go_create_comment) as Button
        myButton!!.setOnClickListener(View.OnClickListener {
            Log.i("MainActivity", "Click Button Collector")
            val i = Intent(context, FormComment::class.java)
            i.putExtra("idalbum", args.albumId.toString())
            startActivity(i)

        })
        viewModel = ViewModelProvider(this, CommentViewModel.Factory(activity.application,args.albumId)).get(
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