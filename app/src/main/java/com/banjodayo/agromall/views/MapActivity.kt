package com.banjodayo.agromall.views

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.banjodayo.agromall.R
import com.banjodayo.agromall.data.Farmer
import com.banjodayo.agromall.data.GeoCoordinate
import com.banjodayo.agromall.databinding.ActivityMapBinding
import com.banjodayo.agromall.utils.EXTRA_FARMER
import com.banjodayo.agromall.utils.EXTRA_LATLNG
import com.banjodayo.agromall.utils.loadCircularImage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*
import kotlin.collections.ArrayList

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding : ActivityMapBinding
    lateinit var farmer : Farmer
    private var polygon : Polygon? = null
    private var geoCoordinate: GeoCoordinate? = null
    private var latLngList = ArrayList<LatLng>()
    private var markers = ArrayList<Marker>()
    private lateinit var mutablePolygon: Polygon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        farmer = intent.getParcelableExtra(EXTRA_FARMER)!!
        geoCoordinate = intent.getParcelableExtra<GeoCoordinate>(EXTRA_LATLNG)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.farm_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        initView()
    }

    private fun initView(){
        val baseImageUrl : String =
            """https://s3-eu-west-1.amazonaws.com/agromall-storage/${farmer.passportPhoto}"""
        binding.layoutMap.farmerLocationTv.text = getString(R.string.username, farmer.city, farmer.address)
        binding.layoutMap.farmerNameTv.text = getString(R.string.username, farmer.first_name, farmer.surname)
        binding.layoutMap.latitudeTv.text = getString(R.string.username, "Latitude",
            geoCoordinate!!.latitude.toString())
        binding.layoutMap.longitudeTv.text = getString(R.string.username, "Longitude",
            geoCoordinate!!.longitude.toString())
        loadCircularImage(this, binding.layoutMap.farmerImage,
            baseImageUrl, R.drawable.ic_profile_image_placeholder)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val center = LatLng(geoCoordinate!!.latitude, geoCoordinate!!.longitude)
       // val center = LatLng(7.606323399999999, 5.1964594)
        val x = geoCoordinate!!.latitude
        val y = geoCoordinate!!.longitude
        with(mMap) {
            setContentDescription("Farm view")
            moveCamera(CameraUpdateFactory.newLatLngZoom(center, 10f))
            addPolyline(PolylineOptions().apply {
                add(center)
                add(LatLng(x + 0.1, y))
                add(LatLng(x + 0.1, y))
                add(LatLng(x + 0.1, y - 0.2))
                add(LatLng(x, y - 0.2))
                add(LatLng(x, y))
            })
        }
    }

    private fun createRectangle(center: LatLng,
            halfWidth: Double,
            halfHeight: Double): List<LatLng>{
        return listOf(
            LatLng(center.latitude - halfHeight, center.longitude - halfWidth),
            LatLng(center.latitude - halfHeight, center.longitude + halfWidth),
            LatLng(center.latitude + halfHeight, center.longitude + halfWidth),
            LatLng(center.latitude + halfHeight, center.longitude - halfWidth),
            LatLng(center.latitude - halfHeight, center.longitude - halfWidth),
        )
    }

    companion object{
        fun newIntent(context: Context, farmer : Farmer? = null, latLng: GeoCoordinate) : Intent {
            return Intent(context, MapActivity::class.java).apply {
                putExtra(EXTRA_FARMER, farmer)
                putExtra(EXTRA_LATLNG, latLng)
            }
        }
    }
}