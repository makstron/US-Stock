package com.klim.stock.favorited.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.klim.coreUi.BaseFragment
import com.klim.coreUi.utils.viewBind
import com.klim.stock.dependencyinjection.view_model.ViewModelFactoryTemp
import com.klim.stock.favorited.ui.databinding.FragmentFavoritedBinding
import com.klim.stock.favorited.ui.di.SymbolsFavoritedComponent
import javax.inject.Inject

class SymbolsFavoritedFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactoryTemp
    private lateinit var vm: SymbolFavoritedViewModel

    private var binding: FragmentFavoritedBinding by viewBind()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    private fun inject() {
        val component = SymbolsFavoritedComponent.Initializer
            .init(getApplicationContextProvider(), getViewModelProviderProvider(), findDependencies())
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoritedBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this, viewModelFactory).get(SymbolFavoritedViewModel::class.java)

        vm.text.observe(viewLifecycleOwner, Observer {
            binding.textHome.text = it
        })

        return binding.root
    }



}