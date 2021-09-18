package com.klim.us_stock.di.settings;

import com.klim.us_stock.ui.windows.settings.SettingsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {SettingsModule.class})
public interface SettingsComponent {

    public void inject(SettingsFragment fragment);

}
