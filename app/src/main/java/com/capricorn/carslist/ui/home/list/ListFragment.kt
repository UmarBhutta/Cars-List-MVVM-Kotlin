package com.capricorn.carslist.ui.home.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.capricorn.carslist.R
import com.capricorn.carslist.data.Resource
import com.capricorn.carslist.data.dto.Car
import com.capricorn.carslist.databinding.FragmentListBinding
import com.capricorn.carslist.ui.home.list.adapter.CarsAdapter
import com.capricorn.carslist.utils.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.*
import com.trafi.anchorbottomsheetbehavior.AnchorBottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val carListFragmentViewModel: ListFragmentViewModel by viewModels()

    @Inject lateinit var fuelUtils: FuelUtils

    @Inject lateinit var transmissionUtils: TransmissionUtils

    @Inject lateinit var interiorUtils: InteriorUtils

    private lateinit var carsAdapter: CarsAdapter
    private lateinit var bottomSheetBehavior: AnchorBottomSheetBehavior<ConstraintLayout>


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
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
        observeViewModel()

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvCarList.layoutManager = layoutManager
        binding.rvCarList.setHasFixedSize(true)
        binding.rvCarList.setDivider(R.drawable.recycler_view_divider)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_ListFragment_to_MapFragment)
        }

        carListFragmentViewModel.getCarList()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showDataView(show: Boolean) {
        binding.noData.visibility = if (show) GONE else VISIBLE
        binding.rvCarList.visibility = if (show) VISIBLE else GONE
        binding.progressBar.gone()
    }

    private fun showLoadingView() {
        binding.progressBar.visible()
        binding.rvCarList.gone()
    }

    private fun bindListData(cars: List<Car>) {
        if (!(cars.isNullOrEmpty())) {
            carsAdapter = CarsAdapter(carListFragmentViewModel, cars)
            binding.rvCarList.adapter = carsAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }


    private fun handleCarsList(status: Resource<List<Car>>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(cars = it) }
            is Resource.Error -> {
                showDataView(false)
                status.errorCode?.let { carListFragmentViewModel.showToastMessage(it) }
            }
        }
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, LENGTH_LONG)
    }

     fun observeViewModel() {
         carListFragmentViewModel.carListLiveData.observe(viewLifecycleOwner,::handleCarsList)
         carListFragmentViewModel.openCarDetails.observe(viewLifecycleOwner,::showCarDetails)
         observeSnackBarMessages(carListFragmentViewModel.showSnackBar)
         observeToast(carListFragmentViewModel.showToast)

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
}