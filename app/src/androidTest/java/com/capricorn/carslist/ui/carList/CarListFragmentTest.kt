package com.capricorn.carslist.ui.carList

import android.os.Bundle
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import com.capricorn.carslist.TestDataUtil.dataSuccess
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.capricorn.carslist.LoadStatus
import com.capricorn.carslist.R
import com.capricorn.carslist.TestDataRepository
import com.capricorn.carslist.launchFragmentInHiltContainer
import com.capricorn.carslist.ui.home.list.ListFragment
import com.capricorn.carslist.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * Created by Muhammad Umar on 13/06/2021.
 *
 * Integration Test for CarList Fragment
 *
 */
@HiltAndroidTest
class CarListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    //Testing repository
    @Inject
    lateinit var repository: TestDataRepository

    private var mIdlingResource: IdlingResource? = null

    @Before
    fun setup(){

        hiltRule.inject()

        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }


    @Test
    fun displayCarList(){

        //tell repo load data
        dataSuccess = LoadStatus.Success

        //launch Car ListFragment
        launchFragmentInHiltContainer<ListFragment>(Bundle.EMPTY, R.style.Base_ThemeOverlay_AppCompat)

        onView(withId(R.id.rvCarList)).check(matches(isDisplayed()))
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))


    }

    @Test
    fun displayNoData(){
        //tell repo to fail
        dataSuccess = LoadStatus.Fail
        //launch Car ListFragment
        launchFragmentInHiltContainer<ListFragment>(Bundle.EMPTY, R.style.Base_ThemeOverlay_AppCompat)

        onView(withId(R.id.rvCarList)).check(matches(not(isDisplayed())))
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.noData)).check(matches(isDisplayed()))
    }


    @Test
    fun testCarDetails() {
        //tell repo to load
        dataSuccess = LoadStatus.Success
        //launch Car ListFragment
        launchFragmentInHiltContainer<ListFragment>(Bundle.EMPTY, R.style.Base_ThemeOverlay_AppCompat)

        onView(withId(R.id.rvCarList))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }


    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister()
        }
    }


}