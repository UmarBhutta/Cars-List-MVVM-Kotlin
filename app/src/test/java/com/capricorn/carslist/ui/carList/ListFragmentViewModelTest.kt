package com.capricorn.carslist.ui.carList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.capricorn.carslist.data.DataRepository
import com.capricorn.carslist.data.Resource
import com.capricorn.carslist.data.dto.Car
import com.capricorn.carslist.data.error.NETWORK_ERROR
import com.capricorn.carslist.ui.home.list.ListFragmentViewModel
import com.capricorn.carslist.util.InstantExecutorExtension
import com.capricorn.carslist.util.MainCoroutineRule
import com.capricorn.carslist.util.TestCarModelsGenerator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Created by Muhammad Umar on 13/06/2021.
 *
 * Unit Testing for the ListFragment View Model
 *
 */

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class ListFragmentViewModelTest {

    //subject under test
    private lateinit var listFragmentViewModel: ListFragmentViewModel

    //Using a mock repo to be injected into the viewmodel
    private val dataRepository: DataRepository = mockk()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var listTitle: String
    private val testModelsGenerator: TestCarModelsGenerator = TestCarModelsGenerator()

    @Before
    fun setup(){
        // initialise the repository with no tasks
        listTitle = testModelsGenerator.getStubTitle()
    }


    @Test
    fun `get Cars List`() {
        // Let's do an answer for the liveData
        val carList = testModelsGenerator.generateCarList()

        //1- Mock calls
        coEvery { dataRepository.requestCarList() } returns flow {
            emit(Resource.Success(carList))
        }

        //2-Call
        listFragmentViewModel = ListFragmentViewModel(dataRepository)
        listFragmentViewModel.getCarList()
        //active observer for livedata
        listFragmentViewModel.carListLiveData.observeForever { }

        //3-verify
        val isEmptyList = listFragmentViewModel.carListLiveData.value?.data?.isNullOrEmpty()
        assertEquals(carList, listFragmentViewModel.carListLiveData.value?.data)
        assertEquals(false,isEmptyList)
    }

    @Test
    fun `get Cars Empty List`() {
        // Let's do an answer for the liveData
        val recipesModel = testModelsGenerator.generateCarListWithEmptyList()

        //1- Mock calls
        coEvery { dataRepository.requestCarList() } returns flow {
            emit(Resource.Success(recipesModel))
        }

        //2-Call
        listFragmentViewModel = ListFragmentViewModel(dataRepository)
        listFragmentViewModel.getCarList()
        //active observer for livedata
        listFragmentViewModel.carListLiveData.observeForever { }

        //3-verify
        val isEmptyList = listFragmentViewModel.carListLiveData.value?.data?.isNullOrEmpty()
        assertEquals(recipesModel, listFragmentViewModel.carListLiveData.value?.data)
        assertEquals(true, isEmptyList)
    }

    @Test
    fun `get Cars List Error`() {
        // Let's do an answer for the liveData
        val error: Resource<List<Car>> = Resource.Error(NETWORK_ERROR)

        //1- Mock calls
        coEvery { dataRepository.requestCarList() } returns flow {
            emit(error)
        }

        //2-Call
        listFragmentViewModel = ListFragmentViewModel(dataRepository)
        listFragmentViewModel.getCarList()
        //active observer for livedata
        listFragmentViewModel.carListLiveData.observeForever { }

        //3-verify
        assertEquals(NETWORK_ERROR, listFragmentViewModel.carListLiveData.value?.errorCode)
    }


}