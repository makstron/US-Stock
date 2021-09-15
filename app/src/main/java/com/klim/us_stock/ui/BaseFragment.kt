package com.klim.us_stock.ui

import androidx.fragment.app.Fragment
import com.klim.windowsmanager.WindowsContainerActivity

abstract class BaseFragment() : Fragment() {

    fun startWindow(fragment: BaseFragment, isItBase: Boolean = false) {
        if (activity is WindowsContainerActivity) {
            (activity as WindowsContainerActivity).startWindow(fragment, isItBase)
        } else {
            throw Exception("I can`t do these. Base Activity should implement WindowsContainerActivity.")
        }
    }

    fun closeWindow() {
        if (activity is WindowsContainerActivity) {
            (activity as WindowsContainerActivity).getActivityViewContainer().onBackPressed()
        } else {
            throw Exception("I can`t do these. Base Activity should implement WindowsContainerActivity.")
        }
    }

}