package com.example.myapplication.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ResultsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<Results>): Completable

    @Query("Select * from movie_table")
    fun getAllCakes(): Flowable<List<Results>>
}