package com.klim.windowsmanager

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import java.lang.Exception
import java.util.*

class WindowsKeeper(var activity: WindowsContainerActivity) {
    private var windows = ArrayList<Window>(3)

    @SuppressLint("ClickableViewAccessibility")
    fun startWindow(fragment: Fragment, isItBase: Boolean = false) {
        val containerView = activity.getActivityViewContainer()

        val frameLayout = FrameLayout(activity.getContext())
        frameLayout.id = View.generateViewId() + Random().nextInt(1000)
        frameLayout.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        frameLayout.isClickable = true
        containerView.addView(frameLayout)

        setFragment(activity, frameLayout.id, fragment, fragment::class.java.simpleName)
        windows.add(Window(fragment, frameLayout, isItBase))
    }

    private fun setFragment(activity: WindowsContainerActivity, targetId: Int, fragment: Fragment, fragmentTag: String?) {
        val fragmentTransaction = activity.getSupportFragmentManager().beginTransaction()
        fragmentTransaction.add(targetId, fragment, fragmentTag)
        fragmentTransaction.commit()
    }

    private fun removeView(window: Window) {
        val containerView = activity.getActivityViewContainer()
        containerView.removeView(window.containerView)
    }

    private fun removeFragment(window: Window) {
        val fragmentTransaction = activity.getSupportFragmentManager().beginTransaction()
        fragmentTransaction.remove(window.fragment)
        fragmentTransaction.commit()
    }

    fun getTopWindow(): Window {
        return if (windows.size > 0)
            windows.last()
        else
            throw Exception("Window was not found. Windows list is empty")
    }

    fun getTopWindowOrNull(): Window? {
        return if (windows.size > 0)
            windows.last()
        else
            null
    }

    fun existsWindowForBackPressed(): Boolean {
        val topWindows = getTopWindowOrNull()
        return if (topWindows != null) {
            !topWindows.base
        } else {
            false
        }
    }

    fun removeTopWindow(): Boolean {
        if (existsWindowForBackPressed()) {
            val topWindow = getTopWindow()
            removeFragment(topWindow)
            removeView(topWindow)
            windows.removeLast()
            return true
        }
        return false
    }
}