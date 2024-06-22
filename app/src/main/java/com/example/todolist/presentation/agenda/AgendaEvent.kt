package com.example.todolist.presentation.agenda

import com.example.todolist.domain.model.Task

sealed class AgendaEvent {
    data class OnTaskCompletionChange(val task: Task) : AgendaEvent()
    data class OnTaskDelete(val task: Task) : AgendaEvent()
    data object OnAddTask : AgendaEvent()
}