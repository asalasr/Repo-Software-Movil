package com.example.vinilos.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.navArgs
import com.example.vinilos.R
import com.example.vinilos.repositories.AlbumRepository
import com.example.vinilos.repositories.PrizeRepository
import com.squareup.picasso.Picasso
import java.util.*


class ArtistDetailFragment : Fragment() {


    private var albumRepositoryObject = AlbumRepository(activity?.application)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_artist_detail, container, false)

        val view:View = inflater.inflate(R.layout.fragment_artist_detail, container, false)
        return view

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val performer: Performer = arguments?.get("performer") as Performer

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val picasso = Picasso.get()

        val rooView = view
        val frv = rooView!!.findViewById<View>(R.id.nameArtist) as TextView
        val frvDescription = rooView!!.findViewById<View>(R.id.descriptionArtist) as TextView
        val frvCreationDate = rooView!!.findViewById<View>(R.id.createArtist) as TextView

        val args: ArtistDetailFragmentArgs by navArgs()
        if(args.artista == null)
            frv.setText("sin artista")

        frv.setText(args.artista.name)
        frvDescription.setText(args.artista.description)
        frvCreationDate.setText(args.artista.createDate)

        picasso.load(args.artista.image)
            .into(rooView!!.findViewById<ImageView>(R.id.imageView2))


       val albumsIds: MutableList<String> = ArrayList()
        albumRepositoryObject = AlbumRepository(activity?.application)
        for (i in 0 until args.artista.albumsId.size){
            albumRepositoryObject.getAlbum( args.artista.albumsId[i],{
                albumsIds.add(i,it.name)
                if(i.equals(args.artista.albumsId.size-1))
                    setListAlbumView(i,albumsIds.toTypedArray())
            }, {

            })

        }

       /* val prizesIds: MutableList<String> = ArrayList()
        albumRepositoryObject = AlbumRepository(activity?.application)
        for (i in 0 until args.artista.prizesId.size){
            albumRepositoryObject.getPrize( args.artista.prizesId[i],{
                prizesIds.add(i,it.name)
                if(i.equals(args.artista.prizesId.size-1))
                    setListPrizeView(i,prizesIds.toTypedArray())
            }, {

            })

        }*/

    }

    fun setListAlbumView(index:Int,albumName:Array<String>){
        val itemsAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.album_item_name)
        for (i in 0 until albumName.size){
            itemsAdapter.add(albumName[i])
        }
        val listView: ListView = view?.findViewById(R.id.listaAlbumes) as ListView
        listView.adapter=itemsAdapter

    }

    /*fun setListPrizeView(index:Int,prizeName:Array<String>){
        val itemsAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.prize_item)
        for (i in 0 until prizeName.size){
            itemsAdapter.add(prizeName[i])
        }
        val listView: ListView = view?.findViewById(R.id.listaPrize) as ListView
        listView.adapter=itemsAdapter

    }*/

}

