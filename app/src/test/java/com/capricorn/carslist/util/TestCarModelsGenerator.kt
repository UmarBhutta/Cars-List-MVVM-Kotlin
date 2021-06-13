package com.capricorn.carslist.util

import com.capricorn.carslist.data.dto.Car
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

/**
 * Created by Muhammad Umar on 13/06/2021.
 */
class TestCarModelsGenerator {

    private var cars:List<Car> = arrayListOf<Car>()

    init {
        val gson = Gson()
        val jsonString = getJson("CarListApiResponse.json")
        val type = TypeToken.getParameterized(ArrayList::class.java,Car::class.java).type
        cars = gson.fromJson(jsonString,type)

        print("car list $cars")
    }

    fun generateCarList(): List<Car> {
        return cars
    }

    fun generateCarListWithEmptyList(): List<Car> {
        return arrayListOf()
    }

    fun generateCarItemModel(): Car {
        return cars[0]
    }

    fun getStubTitle(): String {
        return cars[0].name
    }




    /**
     * Helper function which will load JSON from
     * the path specified
     *
     * @param path : Path of JSON file
     * @return json : JSON from file at given path
     */

    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}