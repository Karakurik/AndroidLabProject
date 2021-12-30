package com.itis.androidlabproject.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.itis.androidlabproject.models.Task

interface TaskDao {
    @Query("SELECT * FROM Task")
    suspend fun getAll(): List<Task>

    @Query("SELECT * FROM Task")
    suspend fun getAllLiveData(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE id IN (:todoIds)")
    suspend fun loadAllByIds(todoIds: IntArray): List<Task>

    @Query("SELECT * FROM Task WHERE title = :title LIMIT 1")
    suspend fun findByName(title: String): Task

    @Query("SELECT * FROM Task WHERE id = :id LIMIT 1")
    suspend fun findById(id: Int): Task

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM Task")
    suspend fun deleteAllTasks()

    @Query("DELETE FROM task WHERE id=:id")
    suspend fun deleteTaskById(id: Int)
}
