package com.itis.androidlabproject.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.itis.androidlabproject.models.Todo

interface TodoDao {
    @Query("SELECT * FROM Todo")
    fun getAll(): List<Todo>

    @Query("SELECT * FROM Todo")
    fun getAllLiveData(): LiveData<List<Todo>>

    @Query("SELECT * FROM Todo WHERE id IN (:todoIds)")
    fun loadAllByIds(todoIds: IntArray): List<Todo>

    @Query("SELECT * FROM Todo WHERE title = :title LIMIT 1")
    fun findByName(title: String): Todo

    @Query("SELECT * FROM Todo WHERE id = :id LIMIT 1")
    fun findById(id: Int): Todo

    @Insert
    fun insert(todo: Todo)

    @Update
    fun update(todo: Todo)

    @Delete
    fun delete(todo: Todo)
}
