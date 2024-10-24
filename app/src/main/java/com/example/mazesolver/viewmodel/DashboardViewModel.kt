package com.example.mazesolver.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mazesolver.model.Data
//import com.example.mazesolver.model.JokeResponse
import com.example.mazesolver.model.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}*/


class DashboardViewModel : ViewModel() {

    private val _joke = MutableLiveData<String>()
    val joke: LiveData<String> = _joke

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Function to fetch jokes
    fun fetchJoke() {
        _isLoading.value = true

        RetrofitInstance.api.getProgrammingJoke().enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    val jokeResponse = response.body()!!
                    _joke.value = when (jokeResponse.type) {
                        "single" -> jokeResponse.joke ?: "No joke available"
                        "twopart" -> "${jokeResponse.setup}\n${jokeResponse.delivery ?: "No delivery available"}"
                        else -> "Unknown joke type"
                    }
                } else {
                    _joke.value = "Failed to retrieve joke"
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                _isLoading.value = false
                _joke.value = "Error: ${t.message}"
            }
        })
    }
}