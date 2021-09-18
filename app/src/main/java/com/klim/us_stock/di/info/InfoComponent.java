package com.klim.us_stock.di.info;

import com.klim.us_stock.ui.windows.info.InfoFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {InfoModule.class})
public interface InfoComponent {

    public void inject(InfoFragment fragment);

}
