package com.capricorn.carslist.ui.component

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.capricorn.carslist.R
import com.capricorn.carslist.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MapFragment : Fragment(),OnMapReadyCallback {

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

        binding.buttonFirst.setOnClickListener {
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

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        with(googleMap) {
            //moveCamera(CameraUpdateFactory.newLatLngZoom(,ZOOM_LEVEL))
            //addMarker(MarkerOptions().position(SYDNEY))
        }
    }


    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
        val ZOOM_LEVEL = 13f
    }
}