package com.example.nandi.roomsample.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "data")
data class Data (

        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,

        @ColumnInfo(name = "name")
        var name: String? = null
    )
