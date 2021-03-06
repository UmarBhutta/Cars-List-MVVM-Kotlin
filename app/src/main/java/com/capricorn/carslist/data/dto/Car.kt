package com.capricorn.carslist.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by Muhammad Umar on 11/06/2021.
 */
data class Car(
    @SerializedName("id") val id : String,
    @SerializedName("modelIdentifier") val modelIdentifier : String,
    @SerializedName("modelName") val modelName : String,
    @SerializedName("name") val name : String,
    @SerializedName("make") val make : String,
    @SerializedName("group") val group : String,
    @SerializedName("color") val color : String,
    @SerializedName("series") val series : String,
    @SerializedName("fuelType") val fuelType : String,
    @SerializedName("fuelLevel") val fuelLevel : Double,
    @SerializedName("transmission") val transmission : String,
    @SerializedName("licensePlate") val licensePlate : String,
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double,
    @SerializedName("innerCleanliness") val innerCleanliness : String,
    @SerializedName("carImageUrl") val carImageUrl : String
)
