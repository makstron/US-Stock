package com.klim.stock.search.ui.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.klim.coreUi.BaseFragment
import com.klim.coreUi.utils.view.extensions.addOnTextChangeEndListener
import com.klim.coreUi.utils.viewBind
import com.klim.stock.dependencyinjection.view_model.ViewModelFactoryTemp
import com.klim.stock.navigation.Navigation
import com.klim.stock.search.ui.databinding.FragmentSearchBinding
import com.klim.stock.search.ui.di.SearchComponent
import com.klim.stock.search.ui.presentation.adapter.SearchResultAdapter
import javax.inject.Inject


class SearchFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactoryTemp
    private lateinit var viewModel: SearchViewModel
    private var binding: FragmentSearchBinding by viewBind()

    @Inject
    lateinit var searchAdapter: SearchResultAdapter
    @Inject
    lateinit var navigation: Navigation

    companion object {
        fun newInstance(args: Bundle): SearchFragment {
            return SearchFragment().apply {
                this.arguments = args
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    private fun inject() {
        val component = SearchComponent.Initializer
            .init(
                getApplicationContextProvider(),
                getViewModelProviderProvider(),
                findDependencies(),
                findDependencies(),
                findDependencies(),
            )
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)

        setActionListeners()
        observeViewModel()

        searchAdapter.clickListener = ::onSearchItemSelected
        binding.searchList.adapter = searchAdapter
        binding.searchList.setHasFixedSize(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchField.requestFocus()

        showKeyboard(binding.searchField, requireContext())
    }

    private fun showKeyboard(editText: EditText, context: Context) {
//        editText.requestFocus()
//        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    fun hideSoftKeyboard(editText: EditText, context: Context) {
//        editText.clearFocus()
//        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    private fun setActionListeners() {
        binding.searchField.addOnTextChangeEndListener { searchRequest ->
            viewModel.search(searchRequest.toString())
        }

        binding.arrowBack.setOnClickListener {
            closeWindow()
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            searchResults.observe(viewLifecycleOwner) { list ->
                searchAdapter.searchResults.clear()
                searchAdapter.searchResults.addAll(list)
                searchAdapter.notifyDataSetChanged()
            }

            isSearching.observe(viewLifecycleOwner) {
                binding.progress.isVisible = it
            }

            isExistsResult.observe(viewLifecycleOwner) {
                binding.emptySearchMessage.isGone = it
            }
        }


    }

    private fun onSearchItemSelected(symbol: String) {
        startWindow(navigation.getDetailsScreen(symbol))
    }

    override fun onDestroy() {
//        hideSoftKeyboard(binding.searchField, requireContext())
        super.onDestroy()
    }
}