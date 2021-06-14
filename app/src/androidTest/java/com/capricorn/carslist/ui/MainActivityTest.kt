package com.capricorn.carslist.ui

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.capricorn.carslist.R
import com.capricorn.carslist.launchFragmentInHiltContainer
import com.capricorn.carslist.ui.home.MainActivity
import com.capricorn.carslist.ui.home.list.ListFragment
import com.capricorn.carslist.ui.home.map.MapFragment
import com.capricorn.carslist.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import com.google.common.truth.Truth.assertThat
import org.junit.After


/**
 * Created by Muhammad Umar on 14/06/2021.
 */

@RunWith(AndroidJUnit4ClassRunner::class)
@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java, false, false)
    private var mIdlingResource: IdlingResource? = null

    @Before
    fun setup(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testLoadCarListFragmentFromMapFragment(){
        //Test Navigation Host Controller
        val navigationController = TestNavHostController(ApplicationProvider.getApplicationContext())



        //Graphical fragment scenario
        launchFragmentInHiltContainer<MapFragment>(navHostController = navigationController){
            navigationController.setGraph(R.navigation.nav_graph)
        }

        //Car List navigation

        InstrumentationRegistry.getInstrumentation().runOnMainSync{

            navigationController.navigate(R.id.action_MapFragment_to_ListFragment)
        }

        assertThat(navigationController.currentDestination?.id).isEqualTo(R.id.ListFragment)
    }



    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister()
        }
    }

}