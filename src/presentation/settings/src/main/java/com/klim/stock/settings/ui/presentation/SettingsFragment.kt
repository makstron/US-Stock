package com.klim.stock.settings.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.klim.coreUi.BaseFragment
import com.klim.coreUi.utils.viewBind
import com.klim.stock.dependencyinjection.view_model.ViewModelFactoryTemp
import com.klim.stock.settings.ui.databinding.FragmentSettingsBinding
import com.klim.stock.settings.ui.di.SettingsComponent
import javax.inject.Inject

class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactoryTemp
    private lateinit var vm: SettingsViewModel
    private var binding: FragmentSettingsBinding by viewBind()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    private fun inject() {
        val component = SettingsComponent.Initializer
            .init(getApplicationContextProvider(), getViewModelProviderProvider())
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        val textView: TextView = binding.textGallery
        vm.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return binding.root
    }

}