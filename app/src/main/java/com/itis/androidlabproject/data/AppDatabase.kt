package com.itis.androidlabproject.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itis.androidlabproject.data.dao.TodoDao
import com.itis.androidlabproject.models.Todo

@Database(
    entities = arrayOf(Todo::class),
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
