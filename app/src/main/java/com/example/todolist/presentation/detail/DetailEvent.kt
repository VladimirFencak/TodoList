package com.example.todolist.presentation.detail

sealed class DetailEvent {
    data object DeleteTask : DetailEvent()
    data object OnTaskCompletionChange : DetailEvent()
}