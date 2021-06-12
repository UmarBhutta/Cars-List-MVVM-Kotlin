package com.capricorn.carslist.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.widget.TextViewCompat

/**
 * Created by Muhammad Umar on 12/06/2021.
 */

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.GONE
}

fun TextView.leftDrawable(startDrawable:Drawable?){
    this.setCompoundDrawables(startDrawable,null,null,null)
}