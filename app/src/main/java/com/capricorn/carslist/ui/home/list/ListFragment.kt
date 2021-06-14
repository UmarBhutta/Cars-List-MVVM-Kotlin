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
 * [ListFragment] Fragment to display list of the Cars fetched through the viewModel
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

        //Setting up AnchorBottomSheetBehavior to display car details
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



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //start observing the view model changes
        observeViewModel()

        //setup Recyclerview
        setupRecyclerView()

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_ListFragment_to_MapFragment)
        }

        // viewModel to get CarList
        carListFragmentViewModel.getCarList()

    }

    /**
     * Setup Recycler View to display List
     */

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvCarList.layoutManager = layoutManager
        binding.rvCarList.setHasFixedSize(true)
        binding.rvCarList.setDivider(R.drawable.recycler_view_divider)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * [showDataView] method used to show data on the screen when it is available from service
     */
    private fun showDataView(show: Boolean) {
        binding.noData.visibility = if (show) GONE else VISIBLE
        binding.rvCarList.visibility = if (show) VISIBLE else GONE
        binding.progressBar.gone()
    }

    /**
     * [showLoadingView] method used to show loading on the screen
     */
    private fun showLoadingView() {
        binding.progressBar.visible()
        binding.rvCarList.gone()
    }

    /**
     * [bindListData] method to build ListView from Data
     */
    private fun bindListData(cars: List<Car>) {
        if (!(cars.isNullOrEmpty())) {
            carsAdapter = CarsAdapter(carListFragmentViewModel, cars)
            binding.rvCarList.adapter = carsAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    /**
     * [handleCarsList] method to handle List of car from ViewModel Observer
     */
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

    /**
     * [observeSnackBarMessages] method show snack bar message
     */
    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, LENGTH_LONG)
    }

    /**
     * [observeToast] method show Toast message
     */
    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, LENGTH_LONG)
    }

    /**
     * [getSlideOffsetForFloatingButton] Slide Offset of floating button relative to [bottomSheet] with its [slideOffset]
     */
    private fun getSlideOffsetForFloatingButton(bottomSheet: View, slideOffset: Float):Float{
        return if(slideOffset <= 0) (bottomSheet.y + bottomSheetBehavior.peekHeight -  binding.floatingActionButton.height-32) else (bottomSheet.y - binding.floatingActionButton.height - 32).toFloat()
    }

     fun observeViewModel() {
         carListFragmentViewModel.carListLiveData.observe(viewLifecycleOwner,::handleCarsList)
         carListFragmentViewModel.openCarDetails.observe(viewLifecycleOwner,::showCarDetails)
         observeSnackBarMessages(carListFragmentViewModel.showSnackBar)
         observeToast(carListFragmentViewModel.showToast)

    }

    /**
     * [setupCarFuelTypeDetails] set current viewing car Fuel Type Details
     */
    private fun setupCarFuelTypeDetails(fuelLevel: Double?,fuelType:String?){

        binding.carBottomSheet.fuelType.text = fuelLevel.toString()
        binding.carBottomSheet.fuelTypeIcon.setImageDrawable(fuelUtils.getFuelTypeIcon(fuelType))
    }

    /**
     * [setupCarTransmissionType] set current viewing car Transmission Type
     */
    private fun setupCarTransmissionType(transmission:String?){
        binding.carBottomSheet.transmissionType.text = transmissionUtils.getTransmissionName(transmission)
        binding.carBottomSheet.transmissionTypeIcon.setImageDrawable(transmissionUtils.getTransmissionIcon(transmission))
    }

    /**
     * [showCarDetails] Method to show Car Details in the bottom sheet
     */
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