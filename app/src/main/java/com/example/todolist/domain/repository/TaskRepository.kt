package com.example.todolist.domain.repository

import com.example.todolist.domain.errors.NetworkError
import com.example.todolist.domain.errors.Result
import com.example.todolist.domain.errors.RoomError
import com.example.todolist.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun getTaskById(taskId: Int): Result<Task, RoomError>
    suspend fun createTask(task: Task): Result<Unit, RoomError>
    suspend fun updateTask(task: Task): Result<Unit, RoomError>
    suspend fun deleteTask(task: Task): Result<Unit, RoomError>
    fun getTasksOrderedByDateCreated(): Result<Flow<List<Task>>, RoomError>
    suspend fun getRemoteTasks(): Result<List<Task>, NetworkError>
}