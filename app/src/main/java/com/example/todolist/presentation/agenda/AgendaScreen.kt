package com.example.todolist.presentation.agenda

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.presentation.components.RoundedTopBar
import com.example.todolist.presentation.components.TaskItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun AgendaScreen() {
    val viewModel = koinViewModel<AgendaViewModel>()
    val state = viewModel.state.value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = { FloatingButton(viewModel = viewModel) }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                RoundedTopBar(
                    title = "Agenda"
                )

                if (state.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .width(50.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(state.tasks) { task ->
                            TaskItem(
                                task = task,
                                onTaskClick = { viewModel.onEvent(AgendaEvent.OnTaskDelete(task)) },
                                onCheckboxClick = { viewModel.onEvent(AgendaEvent.OnTaskCompletionChange(task)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FloatingButton(viewModel: AgendaViewModel) {
    FloatingActionButton(
        onClick = { viewModel.onEvent(AgendaEvent.OnAddTask) },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, "Add Task")
    }
}