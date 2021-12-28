package com.itis.androidlabproject

import android.app.Application
import androidx.room.Room
import com.itis.androidlabproject.data.AppDatabase
import com.itis.androidlabproject.data.dao.TaskDao

class App : Application() {
    lateinit var database: AppDatabase
    lateinit var taskDao: TaskDao

    companion object {
        private lateinit var instance: App

        fun getInstance(): App {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app-db"
        ).run {
            allowMainThreadQueries()
            build()
        }
        taskDao = database.taskDao()
    }
}
