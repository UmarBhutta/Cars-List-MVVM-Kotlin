package com.capricorn.carslist

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.capricorn.carslist.data.dto.Car
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream

/**
 * Created by Muhammad Umar on 13/06/2021.
 */
object TestDataUtil {

    var dataSuccess: LoadStatus = LoadStatus.Success
    var carsList:List<Car> = listOf()

    fun initData():List<Car>{
        val gson = Gson()
        val jsonString = getJson("CarListApiResponse.json")
        val type = TypeToken.getParameterized(ArrayList::class.java,Car::class.java).type
        carsList = gson.fromJson(jsonString,type)
        return carsList
    }


    private fun getJson(path:String):String{
        val ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val inputStream: InputStream = ctx.classLoader.getResourceAsStream(path)
        return inputStream.bufferedReader().use { it.readText() }
    }
}