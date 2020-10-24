package com.banjodayo.agromall.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.banjodayo.agromall.R
import com.banjodayo.agromall.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel : LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.loadingProgress.observe(this){

        }
    }

    //    color: #008737; #689f38;
}