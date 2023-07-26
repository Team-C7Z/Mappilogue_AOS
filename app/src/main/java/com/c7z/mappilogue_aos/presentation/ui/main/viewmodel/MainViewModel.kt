package com.c7z.mappilogue_aos.presentation.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _setLogoTag = MutableLiveData<Boolean>()
    val setLogoTag : LiveData<Boolean> = _setLogoTag

    private val _setTitleTag = MutableLiveData<Boolean>()
    val setTitleTag : LiveData<Boolean> = _setTitleTag

    private val _title = MutableLiveData<String>()
    val title : LiveData<String> = _title

    fun setLogoTag(tag: Boolean) {
        _setLogoTag.value = tag
    }

    fun setTitleTag(tag: Boolean, title: String) {
        _setTitleTag.value = tag
        _title.value = title
    }
}

