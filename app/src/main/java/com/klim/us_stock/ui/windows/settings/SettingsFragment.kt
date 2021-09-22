package com.klim.us_stock.ui.windows.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.klim.us_stock.databinding.FragmentInfoBinding
import com.klim.us_stock.databinding.FragmentSettingsBinding
import com.klim.us_stock.ui.BaseFragment
import com.klim.us_stock.ui.utils.viewBind
import javax.inject.Inject

class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var vm: SettingsViewModel
    private var binding: FragmentSettingsBinding by viewBind()

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponentProvider().getSettingsComponent().inject(this)
        super.onCreate(savedInstanceState)
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

    override fun onDestroy() {
        super.onDestroy()
        getComponentProvider().destroySettingsComponent()
    }
}