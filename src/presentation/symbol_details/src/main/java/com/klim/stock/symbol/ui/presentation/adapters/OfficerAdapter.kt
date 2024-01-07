package com.klim.stock.symbol.ui.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.klim.stock.symbol.ui.databinding.ItemOfficerBinding
import com.klim.stock.symbol.ui.presentation.entity.OfficerEntityView

class OfficerAdapter : RecyclerView.Adapter<OfficerViewHolder>() {

    val data = ArrayList<OfficerEntityView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficerViewHolder {
        return OfficerViewHolder(ItemOfficerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OfficerViewHolder, position: Int) {
        holder.binding.tvName.text = data[position].name
        holder.binding.tvTitle.text = data[position].title
    }

    override fun getItemCount() = data.size

}