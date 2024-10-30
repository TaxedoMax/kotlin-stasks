package com.sbook.kotlinstasks.mainactivity.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sbook.kotlinstasks.entity.Task
import com.sbook.kotlinstasks.mainactivity.viewmodel.TaskListViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskListView(
    selectedDay: LocalDate,
    viewModel: TaskListViewModel = viewModel()
) {
    LaunchedEffect(selectedDay) {
        viewModel.filterTasksByDate(selectedDay)
    }

    val tasks by viewModel.filteredTasks.collectAsState()

    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ) {
        items(tasks, key = { task -> task.id }) { task ->
            TaskItem(
                task = task,
                onTaskUpdated = { updatedTask -> viewModel.updateTask(updatedTask) }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskItem(
    task: Task,
    onTaskUpdated: (Task) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isDone,
            onCheckedChange = { isChecked ->
                onTaskUpdated(task.copy(isDone = isChecked))
            }
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = task.name,
            style = if (task.isDone) {
                MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough)
            } else {
                MaterialTheme.typography.bodyLarge
            },
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = { onTaskUpdated(task.copy(date = task.date.plusDays(1))) }) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_recent_history),
                contentDescription = "Postpone Task"
            )
        }

        IconButton(onClick = { /* TODO: Navigate to edit screen */ }) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_edit),
                contentDescription = "Edit Task"
            )
        }
    }
}