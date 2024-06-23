package com.example.todolist.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.errors.Result
import com.example.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
                        result.data.collect { task ->
                            task?.let {
                                _state.value = _state.value.copy(
                                    isLoading = false,
                                    task = task,
                                    formattedDate = epochSecondsToReadableDate(task.createdAt)
                                )
                            }
                        }
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
            DetailEvent.DeleteTask -> {
                viewModelScope.launch {
                    state.value.task?.let { taskRepository.deleteTask(it) }
                }
            }

            DetailEvent.OnTaskCompletionChange -> {
                viewModelScope.launch {
                    state.value.task?.let { taskRepository.updateTask(it.copy(isCompleted = !it.isCompleted)) }
                }
            }
        }
    }

    private fun epochSecondsToReadableDate(epochMillis: Long): String {
        val instant = Instant.ofEpochSecond(epochMillis)
        val zonedDateTime = instant.atZone(ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        return formatter.format(zonedDateTime)
    }
}