package com.klim.stock.favorited.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.klim.coreUi.BaseFragment
import com.klim.coreUi.extensions.viewModels
import com.klim.coreUi.utils.viewBind
import com.klim.stock.dependencyinjection.view_model.ViewModelFactory
import com.klim.stock.favorited.ui.api.NavigationTarget
import com.klim.stock.favorited.ui.api.SymbolFavoritedViewModel
import com.klim.stock.favorited.ui.databinding.FragmentFavoritedBinding
import com.klim.stock.favorited.ui.di.SymbolsFavoritedComponent
import com.klim.stock.navigation.Navigation
import com.klim.stock.resources.AppTheme
import javax.inject.Inject

class SymbolsFavoritedFragment : BaseFragment() {

    @Inject
    lateinit var navigation: Navigation

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
            .init(
                getApplicationContextProvider(),
                findDependencies(),
                findDependencies(),
                findDependencies(),
            )
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoritedBinding.inflate(inflater, container, false)

        observeViewModel()

        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    FavoritedScreen(viewModel)
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.navigation?.observe(viewLifecycleOwner) { navigationTarget ->
            startWindow(
                when (navigationTarget) {
                    is NavigationTarget.SymbolDetails -> navigation.getDetailsScreen(navigationTarget.symbol)
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }
}