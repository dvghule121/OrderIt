package com.example.orderit.mainViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.orderit.database.*
import com.example.orderit.firebaseDataAdapter.FirebaseDataAdapter
import com.example.shopker.data.User
import com.example.shopker.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val basketList: LiveData<List<Basket>>
    val orderList: LiveData<List<Order>>
    val repository: DataRepository
    private val FBoperator: FirebaseDataAdapter


    init {

        val orderDao = AppDatabase.getDatabase(application).orderDao()
        val basketDao = AppDatabase.getDatabase(application).basketDao()
        val userDao = AppDatabase.getDatabase(application).userDao()

        repository = DataRepository(orderDao, basketDao,userDao)
        FBoperator = FirebaseDataAdapter()
        orderList = repository.orderList
        basketList = repository.basketList





    }
    fun addUser(user:User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }


    fun removeUser() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeUser()
        }
    }


    fun addOrder(order: List<Order>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOrder(order)

        }
    }

    fun addOrder(order:Order) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOrder(order)
            FBoperator.addOrder(order)
        }
    }



    fun adduserOrder(order:Order) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOrder(order)
        }
    }

    fun addBasket(basket: Basket) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBasket(basket)
            FBoperator.addBasket(basket)
        }
    }

    fun updateBasket(basket: Basket){
        viewModelScope.launch (Dispatchers.IO){
            repository.updateBasket(basket)
            FBoperator.updateBasket(basket)
        }
    }

    fun removeBasket(basket: Basket) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeBasket(basket)
            FBoperator.removeBasket(basket_uid = basket.uid.toString())
        }
    }



    fun removeAllBasket() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearBasket()
        }
    }

    fun removeAllOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearOrders()
        }
    }

    fun changeAddress(address:String){
        viewModelScope.launch(Dispatchers.IO) {
            FBoperator.changeAddress(address)
        }
    }





}