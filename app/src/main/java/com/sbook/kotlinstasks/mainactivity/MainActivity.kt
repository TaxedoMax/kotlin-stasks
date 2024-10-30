package com.sbook.kotlinstasks.mainactivity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sbook.kotlinstasks.mainactivity.view.CalendarView
import com.sbook.kotlinstasks.mainactivity.view.TaskListView
import com.sbook.kotlinstasks.mainactivity.viewmodel.TaskListViewModel
import com.sbook.kotlinstasks.ui.theme.KotlinSTasksTheme
import java.time.LocalDate
import java.util.Calendar

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KotlinSTasksTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                    //DatePickerDocked()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content(){
    val taskListViewModel: TaskListViewModel = viewModel()

    var selectedDay by remember { mutableStateOf(LocalDate.now()) }

    Column {
        CalendarView(onDaySelected = { day ->
            selectedDay = day
        })

        TaskListView(selectedDay = selectedDay, viewModel = taskListViewModel)
    }
}
