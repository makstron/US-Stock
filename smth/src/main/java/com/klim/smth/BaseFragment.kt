package com.klim.smth

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.klim.di.ApplicationContextProvider
import com.klim.windowsmanager.WindowsContainerActivity

abstract class BaseFragment() : Fragment() {

    //todo modules
//    fun getApp(): App {
//        return requireActivity().application as App
//    }
//
//    fun getComponentProvider(): ComponentsProvider {
//        return getApp().componentsProvider
//    }

    fun getApplicationContextProvider(): ApplicationContextProvider {
        return requireActivity().application as ApplicationContextProvider
    }

    fun startWindow(fragment: Fragment, isItBase: Boolean = false) {
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

    @ColorInt
    fun getColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(requireContext(), color)
    }

}