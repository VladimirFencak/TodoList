package com.example.todolist.presentation.agenda

import com.example.todolist.domain.errors.ErrorDefault
import com.example.todolist.domain.model.Task

data class AgendaState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: ErrorDefault? = null
)