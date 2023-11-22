package com.example.finaldap

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class sharedViewModel : ViewModel() {
    val data = MutableLiveData<String>()
}