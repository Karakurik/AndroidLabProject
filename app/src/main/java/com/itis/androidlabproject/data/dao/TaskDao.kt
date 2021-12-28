package com.itis.androidlabproject.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.itis.androidlabproject.models.Task

interface TaskDao {
    @Query("SELECT * FROM Task")
    fun getAll(): List<Task>

    @Query("SELECT * FROM Task")
    fun getAllLiveData(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE id IN (:todoIds)")
    fun loadAllByIds(todoIds: IntArray): List<Task>

    @Query("SELECT * FROM Task WHERE title = :title LIMIT 1")
    fun findByName(title: String): Task

    @Query("SELECT * FROM Task WHERE id = :id LIMIT 1")
    fun findById(id: Int): Task

    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("DELETE FROM Task")
    fun deleteAllTasks()

    @Query("DELETE FROM task WHERE id=:id")
    fun deleteTaskById(id: Int)
}
