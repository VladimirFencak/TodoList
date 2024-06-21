package com.example.todolist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.domain.model.Task

@Entity
data class TaskEntity(
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val createdAt: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

fun TaskEntity.toTask() = Task(
    id = id ?: 0,
    title = title,
    description = description ?: "",
    isCompleted = isCompleted,
    createdAt = createdAt.toLong()
)

fun Task.toTaskEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    createdAt = createdAt.toInt()
)