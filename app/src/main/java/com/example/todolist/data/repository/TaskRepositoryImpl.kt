package com.example.todolist.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.example.todolist.data.local.TaskDao
import com.example.todolist.data.local.toTask
import com.example.todolist.data.local.toTaskEntity
import com.example.todolist.data.remote.TaskApi
import com.example.todolist.data.remote.dto.toTask
import com.example.todolist.domain.errors.NetworkError
import com.example.todolist.domain.errors.Result
import com.example.todolist.domain.errors.RoomError
import com.example.todolist.domain.model.Task
import com.example.todolist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskApi: TaskApi,
    private val taskDao: TaskDao
) : TaskRepository {
    override fun getTaskById(taskId: Int): Result<Flow<Task?>, RoomError> {
        return try {
            Result.Success(taskDao.getTaskById(taskId).map { taskEntities ->
                taskEntities?.toTask()
            })
        } catch (e: Exception) {
            Result.Error(getRoomException(e))
        }
    }

    override suspend fun createTask(task: Task): Result<Unit, RoomError> {
        return try {
            taskDao.insert(task.toTaskEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(getRoomException(e))
        }
    }

    override suspend fun updateTask(task: Task): Result<Unit, RoomError> {
        return try {
            taskDao.update(task.toTaskEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(getRoomException(e))
        }
    }

    override suspend fun deleteTask(task: Task): Result<Unit, RoomError> {
        return try {
            taskDao.delete(task.toTaskEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(getRoomException(e))
        }
    }

    override fun getTasksOrderedByDateCreated(): Result<Flow<List<Task>>, RoomError> {
        return try {
            Result.Success(taskDao.getTasksOrderedByDateCreated().map { taskEntities ->
                taskEntities.map { it.toTask() }
            })
        } catch (e: Exception) {
            Result.Error(getRoomException(e))
        }
    }

    override suspend fun getRemoteTasks(): Result<List<Task>, NetworkError> {
        taskApi.getTasks().also { result ->
            return when (result) {
                is Result.Success -> {
                    Result.Success(result.data.map { it.toTask() })
                }

                is Result.Error -> {
                    Result.Error(result.error)
                }
            }
        }
    }

    private fun getRoomException(exception: Exception): RoomError {
        return when (exception) {
            is SQLiteConstraintException -> RoomError.DATABASE_ERROR
            is IllegalArgumentException -> RoomError.ILLEGAL_ARGUMENT
            is IllegalStateException -> RoomError.ILLEGAL_STATE
            else -> RoomError.UNKNOWN_ERROR
        }
    }
}