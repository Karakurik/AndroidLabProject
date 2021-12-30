package com.itis.androidlabproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itis.androidlabproject.data.dao.TaskDao
import com.itis.androidlabproject.models.DateConverter
import com.itis.androidlabproject.models.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private const val DB_NAME = "task.db"

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = Companion.instance ?: synchronized(LOCK) {
            buildDatabase(context)
        }

        private fun buildDatabase(context: Context) {
            instance = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).run {
                allowMainThreadQueries()
                fallbackToDestructiveMigration()
                build()
            }
        }
    }
}
