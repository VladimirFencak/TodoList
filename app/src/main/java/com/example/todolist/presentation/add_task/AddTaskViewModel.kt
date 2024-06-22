package com.example.todolist.presentation.add_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.model.Task
import com.example.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.launch

class AddTaskViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _state = mutableStateOf(AddTaskState())
    val state: State<AddTaskState> = _state

    fun onEvent(event: AddTaskEvent) {
        when (event) {
            is AddTaskEvent.onTitleChange -> {
                _state.value = _state.value.copy(title = event.title)
                if (event.title.isNotBlank()) {
                    _state.value = _state.value.copy(isValidTask = true)
                } else {
                    _state.value = _state.value.copy(isValidTask = false)
                }
            }

            is AddTaskEvent.onDescriptionChange -> {
                _state.value = _state.value.copy(description = event.description)
            }

            AddTaskEvent.onAddTaskTask -> {
                viewModelScope.launch {
                    taskRepository.createTask(
                        Task(
                            title = state.value.title,
                            description = state.value.description,
                            isCompleted = false,
                            id = 0,
                            createdAt = System.currentTimeMillis()
                        )
                    )
                }
            }

            AddTaskEvent.onBack -> TODO()
        }
    }
}