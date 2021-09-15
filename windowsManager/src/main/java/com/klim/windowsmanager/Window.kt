package com.klim.windowsmanager

import android.view.View
import androidx.fragment.app.Fragment

class Window(
    var fragment: Fragment,
    var containerView: View,
    var base: Boolean = false
)