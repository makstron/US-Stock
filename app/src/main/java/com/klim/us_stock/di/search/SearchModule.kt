package com.klim.us_stock.di.search

import com.klim.us_stock.ui.windows.search.adapter.SearchResultAdapter
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @SearchScope
    @Provides
    fun getSearchResultAdapter(): SearchResultAdapter {
        return SearchResultAdapter()
    }

}