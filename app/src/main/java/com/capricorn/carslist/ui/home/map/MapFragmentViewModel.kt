package com.capricorn.carslist.ui.home.map

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capricorn.carslist.data.DataRepositorySource
import com.capricorn.carslist.data.Resource
import com.capricorn.carslist.data.dto.Car
import com.capricorn.carslist.ui.base.BaseViewModel
import com.capricorn.carslist.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
@HiltViewModel
class MapFragmentViewModel @Inject constructor( val dataRepository: DataRepositorySource):BaseViewModel() {


    @VisibleForTesting
    private val carListLiveDataPrivate = MutableLiveData<Resource<List<Car>>>()
    val carListLiveData:LiveData<Resource<List<Car>>> get() = carListLiveDataPrivate

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    @VisibleForTesting

    private val openCarDetailsPrivate = MutableLiveData<SingleEvent<Car>>()
    val openCarDetails: LiveData<SingleEvent<Car>> get() = openCarDetailsPrivate

    /**
     * Error handling as UI
     */
    @VisibleForTesting
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    /**
     * [getCarList] get Car List from he data repository
     */
    fun getCarList(){
        viewModelScope.launch {
            carListLiveDataPrivate.value = Resource.Loading()
            dataRepository.requestCarList().collect {
                carListLiveDataPrivate.value = it
            }
        }
    }

    /**
     * [showCarDetailsBottomSheet] open Car Details when click on Map Marker
     * [carId] is the snippet of the marker used to get Unique car from the list for that specific Map Marker
     */
    fun showCarDetailsBottomSheet(carId:String?){

        val carItem = carListLiveDataPrivate.value?.data?.filter { it.id == carId }.let {
            it?.first()
        }
        openCarDetailsPrivate.value = SingleEvent(carItem!!)
    }

    /**
     * [showToastMessage] show Error Toast Message
     */
    fun showToastMessage(errorCode: Int) {
        val error = errorFactoryManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }
}