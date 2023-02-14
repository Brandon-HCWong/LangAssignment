package com.brandonwong.langassignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brandonwong.langassignment.databinding.ActivityLangBinding


class LangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLangBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}