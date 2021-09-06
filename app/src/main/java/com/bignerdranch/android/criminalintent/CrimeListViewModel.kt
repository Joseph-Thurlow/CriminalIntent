package com.bignerdranch.android.criminalintent

import androidx.lifecycle.ViewModel


//Pull crimes from database.
class CrimeListViewModel : ViewModel() {
    //As the CrimeRepository is already initialized in CriminalIntentApplication get() can be called.
    private val crimeRepository = CrimeRepository.get()
    //Retrieves a list of crimes from the database.
    val crimeListLiveData = crimeRepository.getCrimes()
}