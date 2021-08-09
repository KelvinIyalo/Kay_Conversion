package com.example.abokifx.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.abokifx.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AbokiFX)
        setContentView(R.layout.activity_main)
    }
}