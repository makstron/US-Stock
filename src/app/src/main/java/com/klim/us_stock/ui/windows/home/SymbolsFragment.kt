package com.klim.us_stock.ui.windows.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.klim.smth.BaseFragment
import com.klim.smth.utils.viewBind
import com.klim.us_stock.databinding.FragmentHomeBinding
import com.klim.us_stock.di.home.SymbolsComponent
import javax.inject.Inject

class SymbolsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var vm: SymbolViewModel

    private var binding: FragmentHomeBinding by viewBind()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    private fun inject() {
        val component = SymbolsComponent.Initializer
            .init(getApplicationContextProvider())
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this, viewModelFactory).get(SymbolViewModel::class.java)

        vm.text.observe(viewLifecycleOwner, Observer {
            binding.textHome.text = it
        })

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
//        getComponentProvider().destroySymbolComponent() //todo modules
    }

}