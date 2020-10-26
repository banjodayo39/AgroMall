package com.banjodayo.agromall.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class FarmerList(
    @SerializedName("farmers")
    val farmers :List<Farmer>,
    @SerializedName("totalRec")
    val totalRec :Int,
    @SerializedName("imageBaseUrl")
    val imageBaseUrl: String
)

@Entity(tableName = "farmers")
@Parcelize
data class Farmer(
    @PrimaryKey
    @SerializedName("farmer_id")
    val farmer_id : String,
    @SerializedName("first_name")
    val first_name : String,
    @SerializedName("surname")
    val surname : String,
    @SerializedName("address")
    val address: String,
    @SerializedName("state")
    val state : String,
    @SerializedName("city")
    val city: String,
    @SerializedName("passport_photo")
    var passportPhoto : String?,
) : Parcelable

@Parcelize
data class GeoCoordinate(
    val latitude: Double,
    val longitude: Double
): Parcelable