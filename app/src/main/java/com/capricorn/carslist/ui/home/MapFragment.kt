package com.capricorn.carslist.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.capricorn.carslist.R
import com.capricorn.carslist.data.Resource
import com.capricorn.carslist.data.dto.Car
import com.capricorn.carslist.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class MapFragment : Fragment(),OnMapReadyCallback {

    private val mapFragmentViewModel : MapFragmentViewModel by viewModels()

    private var _binding: FragmentMapBinding? = null
    private lateinit var mapView: MapView


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


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
        binding.buttonFirst.setOnClickListener {
            //findNavController().navigate(R.id.action_MapFragment_to_ListFragment)
            findNavController().navigate(R.id.action_MapFragment_to_CarDetailFragment)
        }

        mapFragmentViewModel.getCarList()
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

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        with(googleMap) {
            //moveCamera(CameraUpdateFactory.newLatLngZoom(,ZOOM_LEVEL))
            //addMarker(MarkerOptions().position(SYDNEY))
        }
    }

    private fun bindListData(cars: List<Car>) {
        if (!(cars.isNullOrEmpty())) {
            cars.forEach{
                println(it.id)
            }
//            recipesAdapter = RecipesAdapter(recipesListViewModel, recipes.recipesList)
//            binding.rvRecipesList.adapter = recipesAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun showDataView(show: Boolean) {
//        binding.tvNoData.visibility = if (show) GONE else VISIBLE
//        binding.rvRecipesList.visibility = if (show) VISIBLE else GONE
//        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
//        binding.pbLoading.toVisible()
//        binding.tvNoData.toGone()
//        binding.rvRecipesList.toGone()
    }

    private fun handleCarsList(status: Resource<List<Car>>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(cars = it) }
            is Resource.Error -> {
                showDataView(false)
                //status.errorCode?.let { recipesListViewModel.showToastMessage(it) }
            }
        }
    }

    private fun observerViewModel(){
        mapFragmentViewModel.carListLiveData.observe(viewLifecycleOwner, ::handleCarsList)
    }


    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
        val ZOOM_LEVEL = 13f
    }
}