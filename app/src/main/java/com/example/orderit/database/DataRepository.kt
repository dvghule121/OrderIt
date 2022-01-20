package com.example.orderit.database

import androidx.lifecycle.LiveData
import com.example.shopker.data.User
import com.example.shopker.data.UserDao


class DataRepository(
    private val orderDao: OrderDao,
    private val basketDao: BasketDao,
    private val userDao: UserDao
) {

    val orderList: LiveData<List<Order>> = orderDao.getAll()
    val userList: LiveData<List<User>> = userDao.getAll()
    val basketList: LiveData<List<Basket>> = basketDao.getAll()



    suspend fun addUser(user: User) {
        userDao.insertUser(user)
    }


    suspend fun removeUser(){
        userDao.delete_all()
    }

    suspend fun addOrder(order: Order) {
        orderDao.insertOrder(order)
    }

    suspend fun addOrder(order: List<Order>) {
        orderDao.insertOrder(order)
    }

    suspend fun addBasket(basket: Basket) {
        basketDao.insertBasket(basket)
    }

    suspend fun updateBasket(basket: Basket){
        basketDao.insertBasket(basket)
    }

    suspend fun clearBasket() {
        basketDao.delete_all()
    }

    suspend fun clearOrders() {
        orderDao.delete_all()
    }

    suspend fun removeBasket(basket: Basket) {
        basketDao.delete(basket)
    }


}