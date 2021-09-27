package com.klim.us_stock.ui.windows.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.klim.us_stock.databinding.FragmentSearchBinding
import com.klim.us_stock.ui.BaseFragment
import com.klim.us_stock.ui.utils.viewBind
import com.klim.us_stock.ui.view_extensions.addOnTextChangeEndListener
import com.klim.us_stock.ui.windows.search.adapter.SearchResultAdapter
import com.klim.us_stock.ui.windows.symbol_details.SymbolDetailsFragment
import javax.inject.Inject


class SearchFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var vm: SearchViewModel
    private var binding: FragmentSearchBinding by viewBind()

    @Inject
    lateinit var searchAdapter: SearchResultAdapter

    companion object {
        fun newInstance(args: Bundle): SearchFragment {
            return SearchFragment().apply {
                this.arguments = args
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponentProvider().getSearchComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
        binding.vm = vm

        setActionListeners()

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
        binding.searchField.addOnTextChangeEndListener {searchRequest ->
            vm.search(searchRequest.toString())
        }

        binding.arrowBack.setOnClickListener {
            closeWindow()
        }

        vm.searchResults.observe(viewLifecycleOwner, { list ->
            searchAdapter.searchResults.clear()
            searchAdapter.searchResults.addAll(list)
            searchAdapter.notifyDataSetChanged()
        })
    }

    private fun onSearchItemSelected(symbol: String) {
        val args = Bundle().apply {
            putString(SymbolDetailsFragment.SYMBOL, symbol)
        }
        startWindow(SymbolDetailsFragment.newInstance(args))
    }

    override fun onDestroy() {
//        hideSoftKeyboard(binding.searchField, requireContext())
        super.onDestroy()
        getComponentProvider().destroySearchComponent()
    }
}