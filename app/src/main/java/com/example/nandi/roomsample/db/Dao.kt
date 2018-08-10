package com.example.nandi.roomsample.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.nandi.roomsample.model.Data

@Dao
interface Dao {
    @Query("SELECT * FROM data")
    fun getAll(): List<Data>

    @Insert
    fun insert(vararg data: Data)

    @Delete
    fun delete(data: Data)
}