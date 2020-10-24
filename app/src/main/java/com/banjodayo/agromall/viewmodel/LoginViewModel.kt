package com.banjodayo.agromall.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banjodayo.agromall.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loadingProgress = MutableLiveData<Int>()
    private var pauseTimer = false

    val loadingProgress: LiveData<Int>
        get() = _loadingProgress

    private var currentProgress = 0.0

    init {
        startTimer()
    }

    private fun startTimer() {
        _loadingProgress.postValue(0)
        loadProgress()
    }

    private fun loadProgress() {
        viewModelScope.launch {
            while (currentProgress < MAX_PROGRESS && !pauseTimer) {
                currentProgress += INCREASE_RATE
                _loadingProgress.value = (currentProgress).toInt()
                Log.e("loading progress", _loadingProgress.value.toString())
                delay(DELAY)
            }
        }
    }


    private fun finishTimer() {
        pauseTimer = true
        _loadingProgress.postValue(100)
    }

    companion object {
        const val MAX_PROGRESS = 90
        private const val DELAY = 50L
        private const val RATE = 1_000L
        private const val MAX_TIME: Long = 20_000L
        private const val INCREASE_RATE: Double = MAX_PROGRESS * RATE / MAX_TIME.toDouble()
    }
}