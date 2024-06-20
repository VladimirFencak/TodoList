package com.example.todolist.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponseDto(
    val id: Int,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val createdAt: Long
)