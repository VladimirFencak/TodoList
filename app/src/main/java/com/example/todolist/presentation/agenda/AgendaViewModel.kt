package com.example.todolist.presentation.agenda

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.errors.Result
import com.example.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.launch

class AgendaViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val _state = mutableStateOf(AgendaState())
    val state: State<AgendaState> = _state

    init {
        fetchTasks()
    }

    fun onEvent(event: AgendaEvent) {
        when (event) {
            AgendaEvent.OnAddTask -> {
                //open add task
            }

            is AgendaEvent.OnTaskDelete -> {
                viewModelScope.launch {
                    taskRepository.deleteTask(event.task)
                }
            }

            is AgendaEvent.OnTaskCompletionChange -> {
                viewModelScope.launch {
                    taskRepository.updateTask(event.task.copy(isCompleted = !event.task.isCompleted))
                }
            }
        }
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = taskRepository.getRemoteTasks()) {
                is Result.Success -> {
                    result.data.forEach {
                        taskRepository.createTask(it)
                    }
                }

                is Result.Error -> {
                    _state.value = _state.value.copy(error = result.error)
                }
            }
            getTasks()
        }
    }

    private fun getTasks() {
        viewModelScope.launch {
            when (val result = taskRepository.getTasksOrderedByDateCreated()) {
                is Result.Success -> {
                    result.data.collect { tasks ->
                        _state.value = _state.value.copy(isLoading = false, tasks = tasks)
                    }
                }

                is Result.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.error)
                }
            }
        }
    }
}