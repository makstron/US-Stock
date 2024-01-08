package com.klim.stock.favorited.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.klim.coreUi.BaseFragment
import com.klim.coreUi.utils.viewBind
import com.klim.stock.dependencyinjection.view_model.ViewModelFactory
import com.klim.stock.favorited.ui.databinding.FragmentFavoritedBinding
import com.klim.stock.favorited.ui.di.SymbolsFavoritedComponent
import javax.inject.Inject

class SymbolsFavoritedFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SymbolFavoritedViewModel by viewModels { viewModelFactory }

    private var binding: FragmentFavoritedBinding by viewBind()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    private fun inject() {
        val component = SymbolsFavoritedComponent.Initializer
            .init(getApplicationContextProvider(), findDependencies())
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoritedBinding.inflate(inflater, container, false)

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textHome.text = it
        })
    }


}