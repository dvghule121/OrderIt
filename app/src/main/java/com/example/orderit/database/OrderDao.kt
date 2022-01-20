package com.example.orderit.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders")
    fun getAll(): LiveData<List<Order>>

    @Query("SELECT * FROM orders WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Order>

    @Query("SELECT * FROM orders WHERE order_name LIKE :name")
    fun findByName(name: String): Order

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: List<Order>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Delete
    fun delete(order: Order)

    @Query("DELETE FROM orders")
    fun delete_all()
}