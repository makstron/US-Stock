package com.klim.us_stock.di.symbol_details;

import com.klim.us_stock.ui.windows.symbol_details.SymbolDetailsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {SymbolDetailsModule.class})
public interface SymbolDetailsComponent {

    public void inject(SymbolDetailsFragment fragment);

}
