package com.klim.us_stock.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.klim.us_stock.databinding.SearchItemBinding
import com.klim.us_stock.ui.search.SearchResultView

class SearchResultAdapter: RecyclerView.Adapter<SearchResultViewHolder>() {

    val searchResults = ArrayList<SearchResultView>()
    var clickListener: ((ticker: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val vh = SearchResultViewHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        vh.itemView.setOnClickListener {
            clickListener?.invoke(it.tag as String)
        }
        return vh
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.binding.labelSymbol.text = searchResults[position].tickerStyled
        holder.binding.labelCompanyName.text = searchResults[position].companyStyled
        holder.itemView.tag = searchResults[position].ticker
    }

    override fun getItemCount() = searchResults.size

}