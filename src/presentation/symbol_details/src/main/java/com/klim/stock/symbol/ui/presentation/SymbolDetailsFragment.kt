package com.klim.stock.symbol.ui.presentation

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.klim.coreUi.BaseFragment
import com.klim.coreUi.extensions.viewModels
import com.klim.coreUi.utils.viewBind
import com.klim.stock.dependencyinjection.view_model.ViewModelFactory
import com.klim.stock.navigation.Navigation
import com.klim.stock.symbol.ui.databinding.FragmentSymbolDetailsBinding
import com.klim.stock.symbol.ui.di.SymbolDetailsComponent
import com.klim.stock.symbol.ui.presentation.adapters.OfficerAdapter
import com.klim.stock.symbol.ui.presentation.adapters.RecommendedAdapter
import com.klim.stock.symbol.ui.presentation.entity.DetailsResultView
import com.klim.stock.symbol.ui.presentation.entity.PriceEntityView
import javax.inject.Inject
import com.klim.stock.resources.R as Res


class SymbolDetailsFragment : BaseFragment(), OnMapReadyCallback {

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SymbolDetailsViewModel by viewModels { viewModelFactory }
    private var binding: FragmentSymbolDetailsBinding by viewBind()
    private var addressMap: MapView? = null

    private var googleMap: GoogleMap? = null

    private val officerAdapter = OfficerAdapter()
    private val recommendedAdapter = RecommendedAdapter()

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
                findDependencies(),
                findDependencies(),
                findDependencies(),
                findDependencies(),
                findDependencies(),
                findDependencies(),
                findDependencies(),
                findDependencies(),
            )
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSymbolDetailsBinding.inflate(inflater, container, false)
        addressMap = binding.addressMap

        viewModel.loadArguments(arguments)
        setActionListeners()
        observeViewModel()

        binding.officersContainer.adapter = officerAdapter
        binding.relatedStocksContainer.adapter = recommendedAdapter

        binding.addressMap.onCreate(null)
        binding.addressMap.getMapAsync(this)

        viewModel.loadDetails()

        return binding.root
    }


    private fun setActionListeners() {
        binding.apply {
            arrowBack.setOnClickListener {
                closeWindow()
            }

            ivFavorite.setOnClickListener {
                viewModel.setFavorited()
            }

            descriptionContent.setOnClickListener {
                showHideFullDescription()
            }
            iconOpenMoreDescription.setOnClickListener {
                showHideFullDescription()
            }
        }

        recommendedAdapter.clickListener = ::onSimilarSymbolItemSelected

    }

    private fun observeViewModel() {
        viewModel.apply {

            detailsResults.observe(viewLifecycleOwner, { details ->
                setContent(details)
            })

            geocodedAddress.observe(viewLifecycleOwner) { location ->
                setLocationOnMap(location)
            }

            price.observe(viewLifecycleOwner) { prices ->
                setPrices(prices)
            }

            chart.observe(viewLifecycleOwner) { prices ->
                prices?.let {
                    binding.chart.setData(prices, getColor(Res.color.brand_red))
                    binding.labelChartErrorMessage.visibility = View.GONE
                } ?: run {
                    binding.labelChartErrorMessage.visibility = View.VISIBLE
                }
            }

            favorited.observe(viewLifecycleOwner) { isFavorited ->
                updateFavoritedIcon(isFavorited)
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
            tvSymbol.minimumWidth = 0
            tvSymbol.minWidth = 0

            companyName.text = details.name

            sectorValue.text = details.sector
            industryValue.text = details.industry
            if (details.ceo?.isNotEmpty() == true) {
                ceoValue.text = details.ceo
            } else {
                labelCEO.isVisible = false
                ceoValue.isVisible = false
            }

            employeesValue.text = details.employees

            address.text = details.address
            addressThumbOne.visibility = View.GONE
            addressThumbTwo.visibility = View.GONE

            phone.text = details.phone

            descriptionContent.text = details.description
            descriptionContentThumbOne.visibility = View.GONE
            descriptionContentThumbTwo.visibility = View.GONE

            ///
            officerAdapter.data.clear()
            officerAdapter.data.addAll(details.officers)
            officerAdapter.notifyDataSetChanged()

            ///
            recommendedAdapter.data.clear()
            recommendedAdapter.data.addAll(details.recommendedSymbols)
            recommendedAdapter.notifyDataSetChanged()
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

            priceDeltaThumb.visibility = View.GONE
        }
    }

    private fun showHideFullDescription() {
        if (descriptionIsOpen) {
            binding.iconOpenMoreDescription.setImageResource(Res.drawable.anim_description_close)
            binding.descriptionContent.maxLines = 2
        } else {
            binding.iconOpenMoreDescription.setImageResource(Res.drawable.anim_description_open)
            binding.descriptionContent.maxLines = 100
        }
        val avd = binding.iconOpenMoreDescription.drawable as AnimatedVectorDrawable
        avd.start()

        descriptionIsOpen = !descriptionIsOpen
    }

    private fun updateFavoritedIcon(isFavorited: Boolean) {
        val icon = if (isFavorited) {
            Res.drawable.ic_star_full
        } else {
            Res.drawable.ic_star_empty
        }
        binding.ivFavorite.setImageResource(icon)
    }

    private fun onSimilarSymbolItemSelected(ticker: String) {
        startWindow(navigation.getDetailsScreen(ticker))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        googleMap.setMinZoomPreference(0f)
        googleMap.uiSettings.setAllGesturesEnabled(false)

        viewModel.geocodedAddress.value?.let { value ->
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

    override fun onLowMemory() {
        super.onLowMemory()
        binding.addressMap.onLowMemory()
    }
}