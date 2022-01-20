package com.example.shopker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "mobile") val mobile: String?,
    @ColumnInfo(name = "email") val email: String?,

)


