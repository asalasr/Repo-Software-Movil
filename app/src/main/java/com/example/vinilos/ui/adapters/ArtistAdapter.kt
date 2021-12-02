package com.example.vinilos.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.ArtistItemBinding
import com.example.vinilos.models.Performer
import com.example.vinilos.ui.ListArtistFragmentDirections
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ArtistAdapter : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>(){

    val picasso = Picasso.get()
    var performers: List<Performer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistViewHolder {
        Log.i("VieModelPermormer","llego aca super bien")
        val withDataBinding: ArtistItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ArtistViewHolder.LAYOUT,
            parent,
            false
        )
        return ArtistViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {

        picasso.load(performers[position].image)
            .into(holder.itemView.findViewById<ImageView>(R.id.imageView2))
        holder.viewDataBinding.also {
            it.artist = performers[position]
        }
        holder.viewDataBinding.root.setOnClickListener {

            // Navigate using that action
            Log.i("Clic en un artista", "se dio clic en artista: " + performers[position].name)
            var artista = performers[position]
            //val albumsIds: Array<Int>(0, 5)
            //var albumsIds: Array<String> = Array(6) { "" }
            val albumsIds: MutableList<Int> = ArrayList()
            val prizesIds: MutableList<Int> = ArrayList()

            if (artista == null) {
                artista = Performer(
                    1,
                    "juan",
                    "no image",
                    "descripcion",
                    createDate = "123123",
                    albumsId = albumsIds.toTypedArray(),
                    prizesId = prizesIds.toTypedArray()
                )
            }

            val action = ListArtistFragmentDirections.actionListArtistFragmentToArtistDetailFragment(
                artista
            )
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return performers.size
    }

    class ArtistViewHolder(val viewDataBinding: ArtistItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.artist_item
        }
    }
}