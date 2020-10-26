package com.banjodayo.agromall.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Message
import java.io.IOException
import java.lang.StringBuilder
import java.util.*

class GeoLocation {

    fun getAddress(locationAddress: String, context: Context, handler: Handler){
        val thread = Thread(){
            @Override
            fun run(){
                val geocoder = Geocoder(context, Locale.getDefault())
                var result : String? = null
                try {
                    var addressList = geocoder.getFromLocationName(locationAddress, 1)
                    if (addressList == null) {
                        addressList = geocoder.getFromLocationName("Nigeria", 1)
                    } else{
                        val address = addressList[0] as Address
                        val stringBuilder = StringBuilder()
                        stringBuilder.append(address.latitude).append("\t")
                        stringBuilder.append(address.longitude)
                        result = stringBuilder.toString()
                    }
                } catch (e: IOException){
                    e.printStackTrace()
                } finally {
                    val message = Message.obtain()
                    message.target = handler
                    if (result != null){
                        message.what = 1
                        val bundle = Bundle()
                        bundle.putString("address", result)
                        message.data = bundle
                    }
                    message.sendToTarget()
                }
            }
        }
        thread.start()
    }
}