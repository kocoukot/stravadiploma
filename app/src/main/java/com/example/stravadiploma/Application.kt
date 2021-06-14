package com.example.stravadiploma

import android.app.Application
import com.example.stravadiploma.database.Database

class Application: Application() {
    override fun onCreate() {
        super.onCreate()

        Database.init(this)
    }
}