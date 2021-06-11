package com.capricorn.carslist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capricorn.carslist.data.DataRepository
import com.capricorn.carslist.data.DataRepositorySource
import com.capricorn.carslist.data.Resource
import com.capricorn.carslist.data.dto.Car
import com.capricorn.carslist.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
@HiltViewModel
class MapFragmentViewModel @Inject constructor(val dataRepository: DataRepositorySource):BaseViewModel() {

    val carListLiveDataPrivate = MutableLiveData<Resource<List<Car>>>()
    val carListLiveData:LiveData<Resource<List<Car>>> get() = carListLiveDataPrivate


    fun getCarList(){
        viewModelScope.launch {
            carListLiveDataPrivate.value = Resource.Loading()
            dataRepository.requestCarList().collect {
                carListLiveDataPrivate.value = it
            }
        }
    }

}