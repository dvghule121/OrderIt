package com.example.orderit.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "baskets")
class Basket(
    @PrimaryKey(autoGenerate = false) val uid: Int,
    @ColumnInfo(name = "basket_name") val name: String?,
    @ColumnInfo(name = "basket_price") val price: Int?,
    @ColumnInfo(name = "basket_qty") val qty:Int?,
    @ColumnInfo(name = "basket_img") val img: Int?,
)