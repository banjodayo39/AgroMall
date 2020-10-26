package com.banjodayo.agromall.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.banjodayo.agromall.R
import com.banjodayo.agromall.data.Farmer
import com.banjodayo.agromall.databinding.ActivityFarmerBinding
import com.banjodayo.agromall.utils.Status
import com.banjodayo.agromall.viewmodel.FarmerViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class FarmerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFarmerBinding
    private  val viewModel : FarmerViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityFarmerBinding>(
            this, R.layout.activity_farmer
        )
        viewModel.getFarmerData().observe(this){
            when(it.status){
                Status.LOADING -> {
                    binding.layoutContent.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.layoutContent.progressBar.visibility = View.GONE
                    if (it.data!!.equals(null)){
                        Toast.makeText(this, getString(R.string.err_message), Toast.LENGTH_SHORT).show()
                    }
                }
                Status.ERROR -> {
                    binding.layoutContent.progressBar.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.err_message), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}