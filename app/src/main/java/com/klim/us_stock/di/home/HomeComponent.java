package com.klim.us_stock.di.home;

import com.klim.us_stock.ui.windows.home.HomeFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {HomeModule.class})
public interface HomeComponent {

    public void inject(HomeFragment fragment);

}
