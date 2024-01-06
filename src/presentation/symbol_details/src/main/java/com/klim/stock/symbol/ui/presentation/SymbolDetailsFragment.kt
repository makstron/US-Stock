package com.klim.stock.symbol.ui.presentation

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.klim.coreUi.BaseFragment
import com.klim.coreUi.utils.viewBind
import com.klim.stock.dependencyinjection.view_model.ViewModelFactoryTest
import com.klim.stock.symbol.ui.databinding.FragmentSymbolDetailsBinding
import com.klim.stock.symbol.ui.di.SymbolDetailsComponent
import com.klim.stock.symbol.ui.presentation.adapters.SimilarAdapter
import com.klim.stock.symbol.ui.presentation.adapters.TagsAdapter
import com.klim.stock.symbol.ui.presentation.entity.DetailsResultView
import com.klim.stock.symbol.ui.presentation.entity.PriceEntityView
import javax.inject.Inject
import com.klim.stock.resources.R as RR


class SymbolDetailsFragment : BaseFragment(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelFactoryTest
    private lateinit var vm: SymbolDetailsViewModel
    private var binding: FragmentSymbolDetailsBinding by viewBind()
    private var addressMap: MapView? = null

    private var googleMap: GoogleMap? = null

    private val tagsAdapter = TagsAdapter()
    private val similarAdapter = SimilarAdapter()

    private var descriptionIsOpen = false
    private var locationWasSetOnMap: Boolean = false

    companion object {
        const val SYMBOL = "symbol"

        fun newInstance(args: Bundle): SymbolDetailsFragment {
            return SymbolDetailsFragment().apply {
                this.arguments = args
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    private fun inject() {
        val component = SymbolDetailsComponent.Initializer
            .init(
                getApplicationContextProvider(),
                getViewModelProviderProvider(),
                findDependencies(),
                findDependencies(),
                findDependencies(),
                findDependencies(),
                findDependencies()
            )
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSymbolDetailsBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this, viewModelFactory).get(SymbolDetailsViewModel::class.java)
        addressMap = binding.addressMap

        vm.loadArguments(arguments)
        setActionListeners()
        observeViewModel()

        binding.tagsContainer.adapter = tagsAdapter
        binding.relatedStocksContainer.adapter = similarAdapter

        binding.addressMap.onCreate(null)
        binding.addressMap.getMapAsync(this)

        vm.loadDetails()

        return binding.root
    }


    private fun setActionListeners() {
        binding.arrowBack.setOnClickListener {
            closeWindow()
        }

        similarAdapter.clickListener = ::onSimilarSymbolItemSelected

        binding.descriptionContent.setOnClickListener {
            showHideFullDescription()
        }
        binding.iconOpenMoreDescription.setOnClickListener {
            showHideFullDescription()
        }
    }

    private fun observeViewModel() {
        vm.apply {

            detailsResults.observe(viewLifecycleOwner, { details ->
                setContent(details)
            })

            geocodedAddress.observe(viewLifecycleOwner) { location ->
                setLocationOnMap(location)
            }

            price.observe(viewLifecycleOwner) { prices ->
                setPrices(prices)
            }

            history.observe(viewLifecycleOwner) { prices ->
                prices?.let {
                    binding.chart.setData(prices, getColor(RR.color.brand_red))
                    binding.labelChartErrorMessage.visibility = View.GONE
                } ?: run {
                    binding.labelChartErrorMessage.visibility = View.VISIBLE
                }
            }

            isLoading.observe(viewLifecycleOwner) { status ->
                binding.progress.isVisible = status
            }
            isExistsResult.observe(viewLifecycleOwner) { result ->
                binding.content.isVisible = result
                binding.emptyMessage.isVisible = !result
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setContent(details: DetailsResultView) {
        binding.apply {
            tvSymbol.text = details.symbol
            tvSymbol.background = null
            tvSymbol.minimumWidth = 0
            tvSymbol.minWidth = 0

            companyName.text = details.name
            companyName.background = null

            sectorValue.text = details.sector
            sectorValue.background = null
            industryValue.text = details.industry
            industryValue.background = null
            ceoValue.text = details.ceo
            ceoValue.background = null
            employeesValue.text = details.employees
            employeesValue.background = null

            address.text = details.address
            addressThumbOne.visibility = View.GONE
            addressThumbTwo.visibility = View.GONE

            phone.text = details.phone
            phone.background = null

            descriptionContent.text = details.description
            descriptionContentThumbOne.visibility = View.GONE
            descriptionContentThumbTwo.visibility = View.GONE

            ///
            tagsAdapter.tagsList.clear()
            tagsAdapter.tagsList.addAll(details.tags)
            tagsAdapter.notifyDataSetChanged()

            ///
            similarAdapter.similarList.clear()
            similarAdapter.similarList.addAll(details.similar)
            similarAdapter.notifyDataSetChanged()
        }
    }

    private fun setLocationOnMap(location: LatLng?) {
        if (!locationWasSetOnMap && googleMap != null) {
            if (location != null) {
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, 14f)
                googleMap?.animateCamera(cameraUpdate)

                val marker = MarkerOptions()
                marker.position(location)
                googleMap?.addMarker(marker)

                locationWasSetOnMap = true

                binding.mapsNoAddresses.visibility = View.GONE
            } else {
                binding.mapsNoAddresses.visibility = View.VISIBLE
            }
        }
    }

    private fun setPrices(prices: PriceEntityView) {
        binding.apply {
            price.text = prices.currentPrice
            priceDelta.text = prices.priceDifferent
            priceDeltaPercent.text = prices.priceDifferentPercent

            priceDelta.setTextColor(prices.color)
            priceDeltaPercent.setTextColor(prices.color)

            priceChangeIcon.setColorFilter(prices.color, PorterDuff.Mode.SRC_ATOP)
            priceChangeIcon.setImageResource(prices.arrow)
            priceChangeIcon.visibility = View.VISIBLE

            price.background = null
            priceDeltaThumb.visibility = View.GONE
        }
    }

    private fun showHideFullDescription() {
        if (descriptionIsOpen) {
            binding.iconOpenMoreDescription.setImageResource(RR.drawable.anim_description_close)
            binding.descriptionContent.maxLines = 2
        } else {
            binding.iconOpenMoreDescription.setImageResource(RR.drawable.anim_description_open)
            binding.descriptionContent.maxLines = 100
        }
        val avd = binding.iconOpenMoreDescription.drawable as AnimatedVectorDrawable
        avd.start()

        descriptionIsOpen = !descriptionIsOpen
    }

    private fun onSimilarSymbolItemSelected(ticker: String) {
        val args = Bundle().apply {
            putString(SYMBOL, ticker)
        }
        startWindow(newInstance(args))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        googleMap.setMinZoomPreference(0f)
        googleMap.uiSettings.setAllGesturesEnabled(false)

        vm.geocodedAddress.value?.let { value ->
            setLocationOnMap(value)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.addressMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.addressMap.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        addressMap?.onDestroy()
        addressMap = null
    }

    override fun onDestroy() {
        super.onDestroy()
//        getComponentProvider().destroySymbolDetailsComponent()//todo modules keep component in other place
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.addressMap.onLowMemory()
    }
}