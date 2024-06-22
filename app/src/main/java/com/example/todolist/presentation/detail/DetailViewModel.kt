package com.example.todolist.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.errors.Result
import com.example.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val taskRepository: TaskRepository,
    private val taskId: Int
) : ViewModel() {
    private val _state = mutableStateOf(DetailState())
    val state: State<DetailState> = _state

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            taskRepository.getTaskById(taskId).also { result ->
                when (result) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(isLoading = false, task = result.data)
                    }

                    is Result.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.OnIsCompletedChange -> {
                viewModelScope.launch {
                    state.value.task?.let { taskRepository.updateTask(it.copy(isCompleted = event.isCompleted)) }
                }
            }

            DetailEvent.DeleteTask -> {
                viewModelScope.launch {
                    state.value.task?.let { taskRepository.deleteTask(it) }
                }
            }
        }
    }
}