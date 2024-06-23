package com.example.todolist.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    taskId: Int
) {
    val viewModel = koinViewModel<DetailViewModel>(
        parameters = { parametersOf(taskId) }
    )
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Agenda")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        },
    ) { scaffoldPadding ->
        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .fillMaxWidth()
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                state.task?.let { task ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.onBackground,
                                unselectedColor = MaterialTheme.colorScheme.onBackground
                            ),
                            selected = task.isCompleted,
                            onClick = { viewModel.onEvent(DetailEvent.OnTaskCompletionChange) }
                        )

                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    HorizontalDivider()
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = task.description
                    )

                    HorizontalDivider()
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = "Created at : ${state.formattedDate}"
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    HorizontalDivider()
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            viewModel.onEvent(DetailEvent.DeleteTask)
                            navController.popBackStack()
                        },
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete Task"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "DELETE TASK")
                        }
                    }
                }

            }
        }
    }
}