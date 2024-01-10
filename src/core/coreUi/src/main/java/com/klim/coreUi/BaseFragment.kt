package com.klim.coreUi

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.dicore.Dependency
import com.klim.stock.dicore.DependencyContainer
import com.klim.windowsmanager.WindowsContainerActivity

abstract class BaseFragment() : Fragment() {

    fun getApplicationContextProvider(): ApplicationContextProvider {
        return requireActivity().application as ApplicationContextProvider
    }

    inline fun <reified D : Dependency> Fragment.findDependencies(): D {
        val dependenciesClass = D::class.java
        return (requireActivity().application as DependencyContainer)
            .dependenciesMap[dependenciesClass] as D?
            ?: throw IllegalStateException("No $dependenciesClass provided to DependencyMap ")
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