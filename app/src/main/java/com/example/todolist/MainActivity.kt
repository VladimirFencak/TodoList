package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.todolist.presentation.add_task.AddTaskScreen
import com.example.todolist.presentation.agenda.AgendaScreen
import com.example.todolist.presentation.detail.DetailScreen
import com.example.todolist.ui.navigation.NavAddTaskScreen
import com.example.todolist.ui.navigation.NavAgendaScreen
import com.example.todolist.ui.navigation.NavDetailScreen
import com.example.todolist.ui.theme.TodoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoListTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavAgendaScreen
                ) {
                    composable<NavAgendaScreen> {
                        AgendaScreen(navController)
                    }
                    composable<NavAddTaskScreen> {
                        AddTaskScreen(navController)
                    }
                    composable<NavDetailScreen> {
                        DetailScreen(
                            navController,
                            it.toRoute<NavDetailScreen>().taskId
                        )
                    }
                }
            }
        }
    }
}

