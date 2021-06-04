package com.example.android.newArch.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.newArch.bean.DataBaseBean
import com.example.android.newArch.dao.MomentDao

/**
 * The Room database for this app
 */
@Database(entities = [DataBaseBean::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun momentDao(): MomentDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}

private const val DATABASE_NAME = "moment-db"
