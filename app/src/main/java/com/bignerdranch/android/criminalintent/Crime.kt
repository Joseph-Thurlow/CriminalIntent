package com.bignerdranch.android.criminalintent

import androidx.room.*
import java.util.*

//The annotation indicates that the class defines the structure of a table,
// or set of tables, in the database.
@Entity
data class Crime(@PrimaryKey val id: UUID = UUID.randomUUID(),
                 var title: String = "",
                 var date: Date = Date(),
                 var isSolved: Boolean = false)