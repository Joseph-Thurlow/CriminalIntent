package com.bignerdranch.android.criminalintent.database

import androidx.room.*
import com.bignerdranch.android.criminalintent.Crime

//The annotation tells Room that this class represents a database in your app.
//The first parameter is a list of entity classes.
//The second parameter is the version of the database.
@Database(entities = [ Crime::class ], version = 1, exportSchema = false)
//Tells the database to use the functions in the class to convert types.
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase : RoomDatabase() {

    abstract fun crimeDao(): CrimeDao
}