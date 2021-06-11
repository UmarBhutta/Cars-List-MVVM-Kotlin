package com.capricorn.carslist.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Muhammad Umar on 09/06/2021.
 */
abstract class BaseActivity : AppCompatActivity() {
    abstract fun observeViewModel()
    protected abstract fun initViewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        observeViewModel()
    }
}