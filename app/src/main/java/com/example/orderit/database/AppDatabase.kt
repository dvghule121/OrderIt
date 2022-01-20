package com.example.orderit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shopker.data.User
import com.example.shopker.data.UserDao


@Database(entities = [User::class, Order::class, Basket::class], version = 6, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao

    abstract fun userDao(): UserDao

    abstract fun basketDao(): BasketDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance

                return instance
            }
        }


    }
}