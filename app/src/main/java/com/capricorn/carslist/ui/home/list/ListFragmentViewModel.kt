package com.capricorn.carslist.ui.home.list

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
 * Created by Muhammad Umar on 12/06/2021.
 */
@HiltViewModel
class ListFragmentViewModel @Inject constructor(val dataRepository: DataRepositorySource) : BaseViewModel(){

    @VisibleForTesting
    private val carListLiveDataPrivate = MutableLiveData<Resource<List<Car>>>()
    val carListLiveData: LiveData<Resource<List<Car>>> get() = carListLiveDataPrivate

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
     * [openCarDetails] Open Car Details from the adapter clicklistener call
     */

    fun openCarDetails(car: Car){
        openCarDetailsPrivate.value = SingleEvent(car)
    }

    /**
     * [showToastMessage] show Error Toast Message
     */
    fun showToastMessage(errorCode: Int) {
        val error = errorFactoryManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }

}