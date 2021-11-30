package com.example.vinilos.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.example.vinilos.models.Collector
import com.example.vinilos.repositories.AlbumRepository
import android.widget.ArrayAdapter
import com.example.vinilos.R


class CollectorDetail : Fragment() {

    private var albumRepositoryObject = AlbumRepository(activity?.application)
    companion object {
        fun newInstance() = CollectorDetail()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view:View = inflater.inflate(R.layout.collector_detail_fragment, container, false)
        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var collector: Collector = arguments?.get("collector") as Collector

        val name: TextView? = view?.findViewById(R.id.collectorName)
        name!!.text= "Nombre:  "+collector.name
        val email: TextView? = view?.findViewById(R.id.collectorEmail)
        email!!.text= "Email:  "+collector.email
        val phone: TextView? = view?.findViewById(R.id.collectorPhone)
        phone!!.text= "Phone:  "+collector.telephone

        val albumsIds: MutableList<String> = ArrayList()
        albumRepositoryObject = AlbumRepository(activity?.application)
        for (i in 0 until collector.albumsId.size){
            albumRepositoryObject.getAlbum( collector.albumsId[i],{
                albumsIds.add(i,it.name)
                if(i.equals(collector.albumsId.size-1))
                setListView(i,albumsIds.toTypedArray())
            }, {

            })

        }

    }

    fun setListView(index:Int,albumName:Array<String>){
        val itemsAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.album_item_name)
        for (i in 0 until albumName.size){
            itemsAdapter.add(albumName[i])
        }
        val listView: ListView = view?.findViewById(R.id.listaAlbumes) as ListView
        listView.adapter=itemsAdapter

    }

}