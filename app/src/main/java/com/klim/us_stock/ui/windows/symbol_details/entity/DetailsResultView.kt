package com.klim.us_stock.ui.windows.symbol_details.entity

class DetailsResultView(
    val symbol: String,
    val name: String,
    val sector: String,
    val industry: String,
    val ceo: String,
    val employees: String,
    val address: String,
    val phone: String,
    val description: String,

    val tags: List<TagEntityView>,
    val similar: List<SimilarEntityView>,
)