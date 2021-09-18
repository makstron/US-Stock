package com.klim.us_stock.ui.windows.symbol_details

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.klim.us_stock.App
import com.klim.us_stock.R
import com.klim.us_stock.databinding.FragmentSymbolDetailsBinding
import com.klim.us_stock.di.symbol_details.SymbolDetailsModule
import com.klim.us_stock.ui.BaseFragment
import com.klim.us_stock.ui.windows.symbol_details.adapters.SimilarAdapter
import com.klim.us_stock.ui.windows.symbol_details.adapters.TagsAdapter
import com.klim.us_stock.ui.windows.symbol_details.entity.DetailsResultView
import com.klim.us_stock.ui.windows.symbol_details.entity.PriceEntityView
import javax.inject.Inject


class SymbolDetailsFragment : BaseFragment(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var vm: SymbolDetailsViewModel
    private lateinit var binding: FragmentSymbolDetailsBinding

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).appComponent.getSymbolDetailsComponent(SymbolDetailsModule()).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSymbolDetailsBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this, viewModelFactory).get(SymbolDetailsViewModel::class.java)

        binding.vm = vm

        vm.loadArguments(arguments)
        setActionListeners()

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

        vm.detailsResults.observe(viewLifecycleOwner, { details ->
            setContent(details)
        })

        vm.geocodedAddress.observe(viewLifecycleOwner) { location ->
            setLocationOnMap(location)
        }

        vm.price.observe(viewLifecycleOwner) { prices ->
            setPrices(prices)
        }

        similarAdapter.clickListener = ::onSimilarSymbolItemSelected

        binding.descriptionContent.setOnClickListener {
            showHideFullDescription()
        }
        binding.iconOpenMoreDescription.setOnClickListener {
            showHideFullDescription()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setContent(details: DetailsResultView) {
        binding.apply {
            symbol.text = details.symbol
            symbol.background = null
            companyName.text = details.name
            companyName.background = null

            labelAbout.text = getString(R.string.label_about)
            labelAbout.background = null
            aboutName.text = details.symbol
            aboutName.background = null

            labelSector.text = getString(R.string.label_sector)
            labelSector.background = null
            sectorValue.text = details.sector
            sectorValue.background = null
            labelIndustry.text = getString(R.string.label_industry)
            labelIndustry.background = null
            industryValue.text = details.industry
            industryValue.background = null
            labelCEO.text = getString(R.string.label_ceo)
            labelCEO.background = null
            ceoValue.text = details.ceo
            ceoValue.background = null
            labelEmployees.text = getString(R.string.label_employees)
            labelEmployees.background = null
            employeesValue.text = details.employees
            employeesValue.background = null

            address.text = details.address
            addressThumbOne.visibility = View.GONE
            addressThumbTwo.visibility = View.GONE

            phone.text = details.phone
            phone.background = null

            labelDescription.text = getString(R.string.label_description)
            labelDescription.background = null
            descriptionContent.text = details.description
            descriptionContentThumbOne.visibility = View.GONE
            descriptionContentThumbTwo.visibility = View.GONE

            ///

            labelTags.text = getString(R.string.label_tags)
            labelTags.background = null

            tagsAdapter.tagsList.clear()
            tagsAdapter.tagsList.addAll(details.tags)
            tagsAdapter.notifyDataSetChanged()

            ///

            labelRelatedStocks.text = getString(R.string.label_related_stocks)
            labelRelatedStocks.background = null

            similarAdapter.similarList.clear()
            similarAdapter.similarList.addAll(details.similar)
            similarAdapter.notifyDataSetChanged()
        }
    }

    private fun setLocationOnMap(location: LatLng?) {
        if (location != null && !locationWasSetOnMap && googleMap != null) {
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, 14f)
            googleMap?.animateCamera(cameraUpdate)

            val marker = MarkerOptions()
            marker.position(location)
            googleMap?.addMarker(marker)

            locationWasSetOnMap = true
        }
    }

    private fun setPrices(prices: PriceEntityView?) {
        if (prices == null) return
        binding.apply {
            price.text = prices.currentPrice
            priceDelta.text = prices.priceDifferent
            priceDeltaPercent.text = prices.priceDifferentPercent

            price.background = null
            priceDeltaThumb.visibility = View.GONE

            priceDelta.setTextColor(prices.color)
            priceDeltaPercent.setTextColor(prices.color)

            priceChangeIcon.setColorFilter(prices.color, PorterDuff.Mode.SRC_ATOP)
            priceChangeIcon.setImageResource(prices.arrow)
            priceChangeIcon.visibility = View.VISIBLE
        }
    }

    private fun showHideFullDescription() {
        if (descriptionIsOpen) {
            binding.iconOpenMoreDescription.setImageResource(R.drawable.anim_description_close)
            binding.descriptionContent.maxLines = 2
        } else {
            binding.iconOpenMoreDescription.setImageResource(R.drawable.anim_description_open)
            binding.descriptionContent.maxLines = 100
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val avd = binding.iconOpenMoreDescription.drawable as AnimatedVectorDrawable
            avd.start()
        }
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

        setLocationOnMap(vm.geocodedAddress.value)
    }

    override fun onResume() {
        super.onResume()
        binding.addressMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.addressMap.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.addressMap.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.addressMap.onLowMemory()
    }
}