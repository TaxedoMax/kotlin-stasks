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
import androidx.compose.ui.Modifier
import com.sbook.kotlinstasks.mainactivity.view.CalendarView
import com.sbook.kotlinstasks.ui.theme.KotlinSTasksTheme
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
    Column {
        CalendarView()
    }
}

@ExperimentalMaterial3Api
@Composable
fun DatePickerDocked() {
    val datePickerState = rememberDatePickerState()
    DatePicker(
        state = datePickerState,
        showModeToggle = false,
        dateValidator = { it: Long ->
            val date = Calendar.getInstance()
            date.set(Calendar.LONG, it.toInt())
            date.get(Calendar.DAY_OF_MONTH) == 10
        }
    )
}