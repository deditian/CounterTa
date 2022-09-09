package com.tian.counterta

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory private constructor(application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    private val mApplication: Application = application

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val newInstance = modelClass.getConstructor(Application::class.java)
        return newInstance.newInstance(mApplication)
    }

    companion object {
        private lateinit var viewModelFactory: ViewModelFactory

        fun getInstance(application: Application): ViewModelFactory {
            if (!this::viewModelFactory.isInitialized) {
                viewModelFactory = ViewModelFactory(application)
            }
            return viewModelFactory
        }
    }

}