package com.example.mazesolver.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {
    private val _time = MutableLiveData(10L)
    val time: LiveData<Long> = _time

    private var timer: CountDownTimer? = null

    fun startTimer(onFinish: () -> Unit) {
        timer?.cancel()
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _time.value = millisUntilFinished / 1000
            }

            override fun onFinish() {
                _time.value = 0L
                onFinish()
            }
        }.start()
    }
}
