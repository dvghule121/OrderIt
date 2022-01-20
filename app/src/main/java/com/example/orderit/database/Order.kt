package com.example.orderit.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
class Order(
    @PrimaryKey(autoGenerate = false) val uid: Int,
    @ColumnInfo(name = "order_name") val name: String?,
    @ColumnInfo(name = "order_address") val address: String?,
    @ColumnInfo(name = "order_email") val email: String?,
    @ColumnInfo(name = "order_payment") val payment: String?,
    @ColumnInfo(name = "order_price") val price: Int?,
    @ColumnInfo(name = "order_img") val img: Int?,
    @ColumnInfo(name = "order_date") val date: String?,
    @ColumnInfo(name = "order_status") val status: String?,
    @ColumnInfo(name = "order_qty") val qty: Int?
)