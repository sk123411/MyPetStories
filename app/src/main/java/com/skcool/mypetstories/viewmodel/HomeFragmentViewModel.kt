package com.skcool.mypetstories.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeFragmentViewModel : ViewModel() {

    var userName = MutableLiveData<String>()


    val user: LiveData<String>
        get() = userName


}