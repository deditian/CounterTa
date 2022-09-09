package com.tian.counterta.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tian.counterta.ViewModelFactory
import java.text.NumberFormat
import java.util.*
import kotlin.reflect.KClass

fun Int.toFormatRupiah(): String {
    return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(this.toLong()).replace("Rp", "Rp ")
}

fun <VM : ViewModel> AppCompatActivity.obtainViewModel(viewModel: KClass<VM>): Lazy<VM> {
    return lazy {
        val factory = ViewModelFactory.getInstance(this.application)
        return@lazy ViewModelProvider(this, factory).get(viewModel.java)
    }
}