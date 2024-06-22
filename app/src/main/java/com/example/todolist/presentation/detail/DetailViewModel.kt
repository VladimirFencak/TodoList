package com.example.todolist.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _state = mutableStateOf(DetailState())
    val state: State<DetailState> = _state

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

            DetailEvent.EditTask -> {
                //navigate to edit
            }
        }
    }
}