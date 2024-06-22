package com.example.todolist.data.remote.dto

import com.example.todolist.domain.model.Task
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TaskResponseDto(
    val id: Int,
    val title: String,
    val description: String?,
    val completed: Boolean,
    val createdAt: String
)

fun TaskResponseDto.toTask() = Task(
    id = id,
    title = title,
    description = description ?: "",
    isCompleted = completed,
    createdAt = Instant.parse(createdAt).epochSecond
)

fun Task.toTaskResponseDto() = TaskResponseDto(
    id = id,
    title = title,
    description = description,
    completed = isCompleted,
    createdAt = Instant.ofEpochSecond(createdAt).toString()
)