package com.klim.us_stock.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.klim.us_stock.databinding.FragmentSearchBinding
import com.klim.us_stock.ui.BaseFragment
import com.klim.us_stock.ui.search.adapter.SearchResultAdapter


class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var vm: SearchViewModel

    private val searchAdapter = SearchResultAdapter()

    companion object {
        fun newInstance(args: Bundle): SearchFragment {
            return SearchFragment().apply {
                this.arguments = args
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding.vm = vm

        setActionListeners()

        searchAdapter.clickListener = ::onSearchItemSelected
        binding.searchList.adapter = searchAdapter
        binding.searchList.layoutManager = LinearLayoutManager(requireContext())
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
        binding.searchField.addTextChangedListener { searchRequest ->
            vm.preSearch(searchRequest.toString())
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

    private fun onSearchItemSelected(ticker: String) {
        Toast.makeText(requireContext(), ticker, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        hideSoftKeyboard(binding.searchField, requireContext())
        super.onDestroy()
    }
}