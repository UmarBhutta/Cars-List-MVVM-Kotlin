package com.capricorn.carslist.ui.base

import androidx.lifecycle.ViewModel
import com.capricorn.carslist.errorFactory.ErrorFactory
import javax.inject.Inject

/**
 * Created by Muhammad Umar on 09/06/2021.
 */
open class BaseViewModel :ViewModel(){

    @Inject
    lateinit var errorFactoryManager: ErrorFactory
}