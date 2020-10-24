package com.banjodayo.agromall.views

import android.app.Activity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.banjodayo.agromall.R
import com.banjodayo.agromall.databinding.ActivityFarmerBinding
import com.banjodayo.agromall.utils.Status
import com.banjodayo.agromall.viewmodel.FarmerViewModel
import org.koin.android.ext.android.inject

class FarmerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFarmerBinding
    private  val viewModel : FarmerViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = DataBindingUtil.setContentView<ActivityFarmerBinding>(
            this, R.layout.activity_farmer)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        viewModel.getfarmers.observe(this){
            when(it.status){
                Status.LOADING ->{}
                Status.SUCCESS -> {}
                Status.ERROR -> {}
            }
        }
    }
}