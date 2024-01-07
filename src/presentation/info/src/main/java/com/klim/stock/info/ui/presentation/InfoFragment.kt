package com.klim.stock.info.ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.klim.coreUi.BaseFragment
import com.klim.coreUi.utils.viewBind
import com.klim.stock.dependencyinjection.view_model.ViewModelFactoryTemp
import com.klim.stock.info.ui.databinding.FragmentInfoBinding
import com.klim.stock.info.ui.di.InfoComponent
import javax.inject.Inject

class InfoFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactoryTemp
    private lateinit var vm: InfoViewModel
    private var binding: FragmentInfoBinding by viewBind()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    private fun inject() {
        val component = InfoComponent.Initializer
            .init(getApplicationContextProvider(), getViewModelProviderProvider())
        component.inject(this)
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

}