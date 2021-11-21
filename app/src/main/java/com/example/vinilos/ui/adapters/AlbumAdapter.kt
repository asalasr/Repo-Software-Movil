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
import com.example.vinilos.databinding.AlbumItemBinding
import com.example.vinilos.models.Album
import com.squareup.picasso.Picasso
import com.example.vinilos.ui.AlbumFragmentDirections


class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    val picasso = Picasso.get()

    var albums :List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumAdapter.AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumAdapter.AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumAdapter.AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumAdapter.AlbumViewHolder, position: Int) {

        picasso.load(albums[position].cover)
            .into(holder.itemView.findViewById<ImageView>(R.id.imageView2))
        holder.viewDataBinding.also {
            it.album = albums[position]
        }
        holder.viewDataBinding.root.setOnClickListener {

            // Navigate using that action
            var id = albums[position].id
            if(id == null) {
                id = 100
            }
            Log.i("Clic en un album","se dio clic en un album"+albums[position].name)
            val action = AlbumFragmentDirections.actionAlbumFragmentToCommentFragment(id)
            holder.viewDataBinding.root.findNavController().navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }


    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }

}