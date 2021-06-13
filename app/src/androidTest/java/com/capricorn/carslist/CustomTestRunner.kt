package com.capricorn.carslist

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Created by Muhammad Umar on 13/06/2021.
 * A custom [AndroidJUnitRunner] used to replace the application used in tests with a
 * [CustomTestRunner].
 */


class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}