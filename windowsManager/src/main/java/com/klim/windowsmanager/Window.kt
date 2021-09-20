package com.klim.windowsmanager

import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

class Window(
    var fragment: Fragment,
    var containerView: FrameLayout,
    var base: Boolean = false
)