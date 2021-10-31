package com.klim.symbol_details.presentation.adapters

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.klim.symbol_details.databinding.ItemTagBinding
import com.klim.symbol_details.presentation.entity.TagEntityView

class TagsAdapter: RecyclerView.Adapter<TagViewHolder>() {

    val tagsList = ArrayList<TagEntityView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.binding.tag.text = tagsList[position].tag
        holder.binding.tag.background.setColorFilter(tagsList[position].color, PorterDuff.Mode.SRC_ATOP)
    }

    override fun getItemCount() = tagsList.size

}