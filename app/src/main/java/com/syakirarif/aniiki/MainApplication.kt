package com.syakirarif.aniiki

import android.app.Application
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(applicationContext).build()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}