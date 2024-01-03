package com.klim.stock.symbol.ui.presentation.adapters

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.klim.stock.symbol.ui.databinding.ItemSimilarBinding
import com.klim.stock.symbol.ui.presentation.entity.SimilarEntityView

class SimilarAdapter : RecyclerView.Adapter<SimilarViewHolder>() {

    val similarList = ArrayList<SimilarEntityView>()
    var clickListener: ((ticker: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val vh = SimilarViewHolder(ItemSimilarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        vh.itemView.setOnClickListener {
            clickListener?.invoke(it.tag as String)
        }
        return vh
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        holder.itemView.tag = similarList[position].symbol
        holder.binding.symbol.text = similarList[position].symbol
        holder.binding.symbol.background.setColorFilter(similarList[position].color, PorterDuff.Mode.SRC_ATOP)
    }

    override fun getItemCount() = similarList.size

}