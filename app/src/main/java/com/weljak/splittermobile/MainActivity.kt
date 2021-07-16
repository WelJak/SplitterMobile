package com.weljak.splittermobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.weljak.splittermobile.R
import com.weljak.splittermobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}