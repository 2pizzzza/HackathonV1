package com.example.hackaton.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel:ViewModel() {
    val email = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()
    val token = MutableLiveData<String>()
}