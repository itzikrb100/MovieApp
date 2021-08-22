package com.example.itziktestapplication

import android.app.Application
import android.util.Log

class BLApp: Application() {


    private val TAG: String = BLApp::class.java.simpleName


    override fun onCreate() {
        super.onCreate()

        Log.d(TAG,"onCreate")
    }


}