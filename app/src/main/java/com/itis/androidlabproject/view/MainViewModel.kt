package com.itis.androidlabproject.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.itis.androidlabproject.App
import com.itis.androidlabproject.models.Todo

class MainViewModel : ViewModel() {
    val liveData: LiveData<List<Todo>>

    init {
        liveData = App.getInstance().todoDao.getAllLiveData()
    }
}
