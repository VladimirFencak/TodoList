package com.example.todolist.presentation.detail

import com.example.todolist.domain.errors.ErrorDefault
import com.example.todolist.domain.model.Task

data class DetailState(
    val task: Task? = null,
    val formattedDate: String = "",
    val isLoading: Boolean = false,
    val error: ErrorDefault? = null
)