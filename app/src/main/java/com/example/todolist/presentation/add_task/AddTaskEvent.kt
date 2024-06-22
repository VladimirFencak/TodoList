package com.example.todolist.presentation.add_task

sealed class AddTaskEvent {
    data class onTitleChange(val title: String) : AddTaskEvent()
    data class onDescriptionChange(val description: String) : AddTaskEvent()
    data object onAddTaskTask : AddTaskEvent()
    data object onBack : AddTaskEvent()
}