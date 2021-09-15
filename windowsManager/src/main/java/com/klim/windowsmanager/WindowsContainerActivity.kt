package com.klim.windowsmanager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.klim.windowsmanager.views.WindowsContainer

interface WindowsContainerActivity {
    fun startWindow(fragment: Fragment, isItBase: Boolean = false)

    fun getActivityViewContainer(): WindowsContainer

    fun getSupportFragmentManager(): FragmentManager

    fun getContext(): Context

}