package com.example.orderit.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface BasketDao {
    @Query("SELECT * FROM baskets")
    fun getAll(): LiveData<List<Basket>>


    @Query("SELECT * FROM baskets WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Basket>

    @Query("SELECT * FROM baskets WHERE basket_name LIKE :name")
    fun findByName(name: String): Basket

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBasket(basket: Basket)

    @Update
    suspend fun updateBasket(basket: Basket)

    @Delete
    fun delete(basket: Basket)

    @Query("DELETE FROM baskets")
    fun delete_all()
}