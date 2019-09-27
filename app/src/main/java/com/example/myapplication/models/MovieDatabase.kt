package com.example.myapplication.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Results::class),version = 2, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun resultsDAO(): ResultsDAO

    companion object{

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase?{

            val temInstances = INSTANCE
            if(INSTANCE != null)
            {
                return temInstances
            }
            synchronized(this){
                val instances = Room.databaseBuilder(context.applicationContext,
                    MovieDatabase::class.java, "movie_table"
                ).build()
                INSTANCE = instances

            }
            return INSTANCE
        }
    }

}