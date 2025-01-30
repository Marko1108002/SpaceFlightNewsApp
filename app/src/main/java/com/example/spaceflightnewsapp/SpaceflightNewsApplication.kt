package com.example.spaceflightnewsapp

import android.app.Application
import com.example.spaceflightnewsapp.data.AppContainer
import com.example.spaceflightnewsapp.data.DefaultAppContainer

class SpaceflightNewsApplication : Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}