package com.example.todolist.data.remote

import com.example.todolist.data.remote.dto.TaskResponseDto
import com.example.todolist.domain.errors.NetworkError
import com.example.todolist.domain.errors.Result

interface TaskApi {
    suspend fun getTasks(): Result<List<TaskResponseDto>, NetworkError>
}