package com.example.cinta.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Tap to stop loading and record the angle"
    }
    val text: LiveData<String> = _text
}