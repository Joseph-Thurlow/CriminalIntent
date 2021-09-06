package com.bignerdranch.android.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.android.criminalintent.Crime
import java.util.*

//The annotation lets Room know that this is one of your data access objects.
@Dao
interface CrimeDao {
    //This annotation indicates what a function is doing to the database.
    //The parameter is an SQL command.
    //The LiveData class executes the query on a background thread.
    @Query("SELECT * FROM crime")
    fun getCrimes(): LiveData<List<Crime>>

    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): LiveData<Crime?>
}