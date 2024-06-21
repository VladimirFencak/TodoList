package com.example.todolist.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(taskEntity: TaskEntity): Long

    @Update
    suspend fun update(taskEntity: TaskEntity): Int

    @Delete
    suspend fun delete(taskEntity: TaskEntity): Int

    @Query("SELECT * FROM TaskEntity WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): TaskEntity?

    @Query("SELECT * FROM TaskEntity ORDER BY createdAt ASC")
    fun getTasksOrderedByDateCreated(): Flow<List<TaskEntity>>
}