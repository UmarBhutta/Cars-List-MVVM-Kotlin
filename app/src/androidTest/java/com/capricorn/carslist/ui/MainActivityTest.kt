package com.capricorn.carslist.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.rule.ActivityTestRule
import com.capricorn.carslist.ui.home.MainActivity
import com.capricorn.carslist.utils.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Muhammad Umar on 14/06/2021.
 */

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
    fun navigationGraphTest(){

    }
}