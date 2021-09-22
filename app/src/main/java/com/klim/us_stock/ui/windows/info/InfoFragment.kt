package com.klim.us_stock.ui.windows.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.klim.us_stock.databinding.FragmentInfoBinding
import com.klim.us_stock.ui.BaseFragment
import com.klim.us_stock.ui.utils.viewBind
import javax.inject.Inject

class InfoFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var vm: InfoViewModel
    private var binding: FragmentInfoBinding by viewBind()

    override fun onCreate(savedInstanceState: Bundle?) {
        getComponentProvider().getInfoComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this, viewModelFactory).get(InfoViewModel::class.java)

        val textView: TextView = binding.textSlideshow
        vm.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        getComponentProvider().destroyInfoComponent()
    }
}