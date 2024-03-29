package com.klim.stock.search.ui.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.klim.coreUi.BaseFragment
import com.klim.coreUi.extensions.viewModels
import com.klim.coreUi.utils.viewBind
import com.klim.stock.dependencyinjection.view_model.ViewModelFactory
import com.klim.stock.navigation.Navigation
import com.klim.stock.search.ui.databinding.FragmentSearchBinding
import com.klim.stock.search.ui.di.SearchComponent
import com.klim.stock.search.ui.presentation.adapter.SearchResultAdapter
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SearchViewModel by viewModels { viewModelFactory }
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
                findDependencies(),
                findDependencies(),
                findDependencies(),
            )
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

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
        binding.searchField.doOnTextChanged { text, start, before, count ->
            viewModel.updateSearchRequest(text.toString())
        }

        binding.searchField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search()
            }
            true
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