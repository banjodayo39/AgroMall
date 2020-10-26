package com.banjodayo.agromall.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent

class LoginViewModel : ViewModel() , KoinComponent{

    lateinit var countDownTimer : CountDownTimer

}