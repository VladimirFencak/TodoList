package com.example.todolist.presentation.detail

sealed class DetailEvent {
    data class OnIsCompletedChange(val isCompleted: Boolean) : DetailEvent()
    data object DeleteTask : DetailEvent()
}