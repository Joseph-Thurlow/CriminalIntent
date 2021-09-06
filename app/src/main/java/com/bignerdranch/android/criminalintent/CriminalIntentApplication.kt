package com.bignerdranch.android.criminalintent

import android.app.Application

class CriminalIntentApplication : Application() {

    //This is called by the system when your application is first loaded into memory.
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}