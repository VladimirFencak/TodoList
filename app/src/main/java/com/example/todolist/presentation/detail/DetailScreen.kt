package com.example.todolist.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailScreen(
    navController: NavController,
    taskId: Int
) {
    val viewModel = koinViewModel<DetailViewModel>(
        parameters = { parametersOf(taskId) }
    )
    val state = viewModel.state.value

    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(50.dp)
                .padding(16.dp)
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Delete")
            }

            Spacer(modifier = Modifier.height(16.dp))

            state.task?.let { task ->
                Text(text = task.title)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = task.description)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = task.createdAt.toString())
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = task.isCompleted.toString())
            }

        }
    }
}