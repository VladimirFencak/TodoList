package com.example.todolist.di

import android.util.Log
import androidx.room.Room
import com.example.todolist.data.local.TaskDatabase
import com.example.todolist.data.remote.TaskApi
import com.example.todolist.data.remote.TaskApiImpl
import com.example.todolist.data.repository.TaskRepositoryImpl
import com.example.todolist.domain.repository.TaskRepository
import com.example.todolist.presentation.add_task.AddTaskViewModel
import com.example.todolist.presentation.agenda.AgendaViewModel
import com.example.todolist.presentation.detail.DetailViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(Android) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.e("HttpClient", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
            }
        }
    }

    single {
        Room.databaseBuilder(
            get(),
            TaskDatabase::class.java,
            "task-database"
        ).build()
    }

    single {
        get<TaskDatabase>().taskDao
    }

    single<TaskApi> {
        TaskApiImpl(get())
    }

    single<TaskRepository> {
        TaskRepositoryImpl(get(), get())
    }

    viewModel {
        AgendaViewModel(get())
    }

    viewModel {
        AddTaskViewModel(get())
    }

    viewModel {
        DetailViewModel(get(), get())
    }
}