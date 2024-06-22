package com.example.todolist.presentation.add_task

data class AddTaskState(
    val title: String = "",
    val description: String = "",
    val isValidTask: Boolean = false
)