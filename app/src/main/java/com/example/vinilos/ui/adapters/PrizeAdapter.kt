package com.example.vinilos.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.PrizeItemBinding
import com.example.vinilos.models.Prize

class PrizeAdapter : RecyclerView.Adapter<PrizeAdapter.PrizeViewHolder>() {

    var prizes: List<Prize> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrizeViewHolder {
        val withDataBinding: PrizeItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            PrizeViewHolder.LAYOUT,
            parent,
            false
        )
        return PrizeViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: PrizeViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.prize = prizes[position]
        }
        holder.viewDataBinding.root.setOnClickListener {
            Log.i("Clic en un premio", "se dio clic en un premio" + prizes[position].name)
        }
    }

    override fun getItemCount(): Int {
        return prizes.size
    }


    class PrizeViewHolder(val viewDataBinding: PrizeItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.prize_item
        }
    }


}