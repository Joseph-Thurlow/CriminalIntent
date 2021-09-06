package com.bignerdranch.android.criminalintent

import androidx.lifecycle.ViewModel


//Create a list of 100 fake crimes.
class CrimeListViewModel : ViewModel() {

    val crimes = mutableListOf<Crime>()

    init {
        for (i in 0 until 100) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i%2 == 0
            crimes += crime
        }
    }
}