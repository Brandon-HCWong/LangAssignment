package com.brandonwong.langassignment

import android.app.Application
import com.brandonwong.langassignment.data.Koin

class LangApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Koin.initKoin(this)
    }
}