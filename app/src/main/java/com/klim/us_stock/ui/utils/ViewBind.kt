package com.klim.us_stock.ui.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.reflect.KProperty

public fun <T> viewBind() = ViewBind<T>(null)
public fun <T> viewBind(initializer: () -> T) = ViewBind<T>(initializer)

class ViewBind<T>(private val initializer: (() -> T)?) : LifecycleObserver {

    private var isInitialized = false
    private var isObserverAttached = false

    private var _binding: T? = null

    operator fun getValue(fragment: Fragment, property: KProperty<*>): T {

        if (!isInitialized) {
            if (initializer != null) {
                _binding = initializer.invoke()
                isInitialized = true
            }
        }
        if (!isObserverAttached) {
            fragment.viewLifecycleOwner.lifecycle.addObserver(this)
        }

        if (_binding == null) {
            throw Exception(
                "You have tried to take a view outside of its lifecycle. " +
                        "Before onCreateView() or after onDestroyView()."
            )
        }

        return _binding!!
    }

    operator fun setValue(fragment: Fragment, property: KProperty<*>, fragmentBinding: T) {
        _binding = fragmentBinding
        isInitialized = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyView() {
        isInitialized = false
        _binding = null
        isObserverAttached = false
    }

}