package com.emenjivar.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "username") val userName: String,
    @ColumnInfo(name = "password") val password: String
)