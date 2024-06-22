package com.example.todolist.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object NavAgendaScreen

@Serializable
data class NavDetailScreen(
    val taskId: Int
)

@Serializable
object NavAddTaskScreen