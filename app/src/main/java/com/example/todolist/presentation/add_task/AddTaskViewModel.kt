package com.example.todolist.presentation.add_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.errors.Result
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
            is AddTaskEvent.OnTitleChange -> {
                _state.value = _state.value.copy(title = event.title)
            }

            is AddTaskEvent.OnDescriptionChange -> {
                _state.value = _state.value.copy(description = event.description)
            }

            AddTaskEvent.OnAddTaskTask -> {
                if (state.value.title.isBlank()) {
                    _state.value = _state.value.copy(isMissingTitleError = true)
                } else {
                    _state.value = _state.value.copy(isMissingTitleError = false)
                    viewModelScope.launch {
                        when (taskRepository.createTask(
                            Task(
                                title = state.value.title,
                                description = state.value.description,
                                isCompleted = false,
                                id = 0,
                                createdAt = System.currentTimeMillis() / 1000
                            )
                        )) {
                            is Result.Success -> {
                                _state.value = _state.value.copy(savedSuccessfully = true)
                            }

                            is Result.Error -> {
                                _state.value = _state.value.copy(savedSuccessfully = false)
                            }
                        }
                    }
                }
            }
        }
    }
}