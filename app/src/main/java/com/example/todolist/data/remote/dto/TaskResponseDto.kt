package com.example.todolist.data.remote.dto

import com.example.todolist.domain.model.Task
import kotlinx.serialization.Serializable

@Serializable
data class TaskResponseDto(
    val id: Int,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val createdAt: Long
)

fun TaskResponseDto.toTask() = Task(
    id = id,
    title = title,
    description = description ?: "",
    isCompleted = isCompleted,
    createdAt = createdAt
)

fun Task.toTaskResponseDto() = TaskResponseDto(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    createdAt = createdAt
)