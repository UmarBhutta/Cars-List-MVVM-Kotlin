package com.capricorn.carslist.ui.base

import androidx.lifecycle.ViewModel
import com.capricorn.carslist.usecase.error.ErrorFactoryManager
import javax.inject.Inject

/**
 * Created by Muhammad Umar on 09/06/2021.
 */
class BaseViewModel :ViewModel(){

    @Inject
    lateinit var errorFactoryManager: ErrorFactoryManager
}