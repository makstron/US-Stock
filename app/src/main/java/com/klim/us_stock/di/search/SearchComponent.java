package com.klim.us_stock.di.search;

import com.klim.us_stock.ui.windows.search.SearchFragment;

import dagger.Subcomponent;

@SearchScope
@Subcomponent(modules = {SearchModule.class})
public interface SearchComponent {

    public void inject(SearchFragment fragment);

}
