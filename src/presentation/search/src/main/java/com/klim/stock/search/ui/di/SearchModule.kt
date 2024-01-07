package com.klim.stock.search.ui.di

import com.klim.stock.search.ui.presentation.SearchResultFormatter
import com.klim.stock.search.ui.presentation.adapter.SearchResultAdapter
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @SearchScope
    @Provides
    fun getSearchResultAdapter(): SearchResultAdapter {
        return SearchResultAdapter()
    }

    @SearchScope
    @Provides
    fun getSearchResultFormatter(): SearchResultFormatter {
        return SearchResultFormatter()
    }

}