package com.capricorn.carslist.ui.home.map

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_LONG
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.capricorn.carslist.R
import com.capricorn.carslist.data.Resource
import com.capricorn.carslist.data.dto.Car
import com.capricorn.carslist.databinding.FragmentMapBinding
import com.capricorn.carslist.utils.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.trafi.anchorbottomsheetbehavior.AnchorBottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class MapFragment : Fragment(),OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private val mapFragmentViewModel : MapFragmentViewModel by viewModels()

    @Inject lateinit var fuelUtils: FuelUtils

    @Inject lateinit var transmissionUtils: TransmissionUtils

    @Inject lateinit var interiorUtils: InteriorUtils

    private var _binding: FragmentMapBinding? = null
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var bottomSheetBehavior: AnchorBottomSheetBehavior<ConstraintLayout>



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)

        //getting the map view here
        mapView = binding.map

        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)

        bottomSheetBehavior = AnchorBottomSheetBehavior.from(binding.carBottomSheet.root)
        bottomSheetBehavior.addBottomSheetCallback(object:AnchorBottomSheetBehavior.BottomSheetCallback(){


            override fun onStateChanged(p0: View, p1: Int, p2: Int) {
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.floatingActionButton.animate().y(getSlideOffsetForFloatingButton(bottomSheet,slideOffset)).setDuration(0).start()
            }

        })
        return binding.root

    }

    private fun getSlideOffsetForFloatingButton(bottomSheet: View, slideOffset: Float):Float{
        return if(slideOffset <= 0) (bottomSheet.y + bottomSheetBehavior.peekHeight -  binding.floatingActionButton.height-32) else (bottomSheet.y - binding.floatingActionButton.height - 32).toFloat()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_MapFragment_to_ListFragment)
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY) ?: Bundle().also {
            outState.putBundle(MAPVIEW_BUNDLE_KEY, it)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        mapView.let {
            it.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        mapView.let {
            it.onPause()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mapView.let {
            it.onDestroy()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.let {
            it.onLowMemory()
        }
    }

    override fun onMapReady(_googleMap: GoogleMap?) {
        _googleMap ?: return
        googleMap = _googleMap
        googleMap.setOnMarkerClickListener(this)
        mapFragmentViewModel.getCarList()

    }

    private fun bindListData(cars: List<Car>) {
        hideLoadingView()
        if (!(cars.isNullOrEmpty())) {
            googleMap ?: return
            with(googleMap){
                cars.forEach{
                    addMarker(MarkerOptions().position(LatLng(it.latitude,it.longitude)).snippet(it.id)
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(context?.resources,R.drawable.marker_custom))))
                }
                val lastItem = cars.last()
                moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(lastItem.latitude,lastItem.longitude),
                    ZOOM_LEVEL
               ))
            }
        }
    }

    private fun showLoadingView(){
        binding.progressBar.visible()
    }

    private fun hideLoadingView(){
        binding.progressBar.gone()
    }


    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, LENGTH_LONG)
    }


    private fun setupCarFuelTypeDetails(fuelLevel: Double?,fuelType:String?){

        binding.carBottomSheet.fuelType.text = fuelLevel.toString()
        binding.carBottomSheet.fuelTypeIcon.setImageDrawable(fuelUtils.getFuelTypeIcon(fuelType))
    }

    private fun setupCarTransmissionType(transmission:String?){
        binding.carBottomSheet.transmissionType.text = transmissionUtils.getTransmissionName(transmission)
        binding.carBottomSheet.transmissionTypeIcon.setImageDrawable(transmissionUtils.getTransmissionIcon(transmission))
    }


    private fun showCarDetails(carDetailEvent: SingleEvent<Car>){
        carDetailEvent.getContentIfNotHandled().let { car ->
            //setting up car model Name
            binding.carBottomSheet.carName.text =  car?.modelName
            //setting up cleaning status
            binding.carBottomSheet.cleanStatus.text = interiorUtils.getVehicleCleanliness(car?.innerCleanliness)
            //setting up fuel details
            setupCarFuelTypeDetails(car?.fuelLevel,car?.fuelType)
            //setting up transmission details
            setupCarTransmissionType(car?.transmission)
            //Car Image Loading
            binding.carBottomSheet.carImage.loadImage(car?.carImageUrl)

            bottomSheetBehavior.state = AnchorBottomSheetBehavior.STATE_ANCHORED
        }

    }

    private fun handleCarsList(status: Resource<List<Car>>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(cars = it) }
            is Resource.Error -> {
                status.errorCode?.let { mapFragmentViewModel.showToastMessage(it) }
            }
        }
    }

    private fun observerViewModel(){
        mapFragmentViewModel.carListLiveData.observe(viewLifecycleOwner, ::handleCarsList)
        mapFragmentViewModel.openCarDetails.observe(viewLifecycleOwner,::showCarDetails)
        observeSnackBarMessages(mapFragmentViewModel.showSnackBar)
        observeToast(mapFragmentViewModel.showToast)
    }


    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
        val ZOOM_LEVEL = 13f
    }

    override fun onMarkerClick(marker: Marker): Boolean {

        mapFragmentViewModel.showCarDetailsBottomSheet(marker.snippet)

        return true
    }
}