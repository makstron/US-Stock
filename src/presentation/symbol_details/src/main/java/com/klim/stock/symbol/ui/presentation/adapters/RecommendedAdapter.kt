package com.klim.stock.symbol.ui.presentation.adapters

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.klim.stock.symbol.ui.databinding.ItemRecommendationBinding
import com.klim.stock.symbol.ui.presentation.entity.RecommendedEntityView

class RecommendedAdapter : RecyclerView.Adapter<RecommendedViewHolder>() {

    val data = ArrayList<RecommendedEntityView>()
    var clickListener: ((ticker: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val vh = RecommendedViewHolder(ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        vh.itemView.setOnClickListener {
            clickListener?.invoke(it.tag as String)
        }
        return vh
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        holder.itemView.tag = data[position].symbol
        holder.binding.symbol.text = data[position].symbol
        holder.binding.symbol.background.setColorFilter(data[position].color, PorterDuff.Mode.SRC_ATOP)
    }

    override fun getItemCount() = data.size

}