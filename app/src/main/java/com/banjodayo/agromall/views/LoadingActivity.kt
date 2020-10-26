package com.banjodayo.agromall.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.banjodayo.agromall.R
import com.banjodayo.agromall.databinding.ActivityLoadingBinding
import com.banjodayo.agromall.utils.EXTRA_LOG_OUT
import com.banjodayo.agromall.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoadingActivity : AppCompatActivity() {

    private val viewModel : LoginViewModel by viewModel()
    private lateinit var  binding : ActivityLoadingBinding
    private lateinit var sharedPrefs : SharedPreferences
    private  var time_in_milli_sec = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loading)
        sharedPrefs = this.getSharedPreferences(
            getString(R.string.shared_pref_file_key), Context.MODE_PRIVATE)

        startTimer(5000L)

         val logout = intent.getIntExtra(EXTRA_LOG_OUT, 0)
        if(logout == LOGOUT){
            toggleVisibility()
        }

        binding.loginLayout.loginButton.setOnClickListener {
            if (verifyLogin()) {
                with(sharedPrefs.edit()) {
                    putBoolean("isLogin", true).apply()
                }
                startFarmActivity()
            } else{
                Log.e("email", binding.loginLayout.emailEditText.text.toString())
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }


    }

   private fun verifyLogin(): Boolean{
       return if (binding.loginLayout.emailEditText.text.toString() == getString(R.string.email_value)
           && binding.loginLayout.passwordEditText.text.toString() == getString(
               R.string.password_value)
       ){
           true
       } else {
           if(binding.loginLayout.emailEditText.text.toString() != getString(R.string.email_value))
               message = getString(R.string.err_email)
           else if (binding.loginLayout.passwordEditText.text.toString() != getString(R.string.password_value))
               message = getString(
                          R.string.err_password)
           Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
           false
       }
   }

    private fun startTimer(time_in_seconds: Long){
        viewModel.countDownTimer = object : CountDownTimer(time_in_seconds, 500){
            override fun onTick(p0: Long) {
                val progress = p0/50
                binding.loadingLayout.loadingProgressBar.progress = 100 - progress.toInt()
            }

            override fun onFinish() {
                if(sharedPrefs.contains("isLogin")){
                    if(sharedPrefs.getBoolean("isLogin", false)) {
                        startFarmActivity()
                    } else {
                        toggleVisibility()
                    }
                } else {
                    toggleVisibility()
                }
            }
        }.start()
    }

    private fun startFarmActivity(){
        val intent = Intent(this, FarmerActivity::class.java)
        startActivity(intent)
    }

    private fun toggleVisibility(){
        binding.loadingLayout.root.visibility = View.GONE
        binding.loginGroup.visibility = View.VISIBLE
        sharedPrefs.edit().putBoolean("isLogin", false).apply()
    }

    companion object{
        private  var message  : String= ""
        private const val LOGOUT = 1
    }
}