package com.sbook.kotlinstasks.mainactivity.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sbook.kotlinstasks.mainactivity.viewmodel.CalendarViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(
    calendarViewModel: CalendarViewModel = viewModel(),
    onDaySelected: (LocalDate) -> Unit
) {
    val days by calendarViewModel.days.collectAsState()
    val selectedDay by calendarViewModel.selectedDay.collectAsState()
    val currentMonth by calendarViewModel.currentMonth.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { calendarViewModel.previousWeek() }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_media_previous),
                    contentDescription = "Previous week"
                )
            }

            Text(
                text = currentMonth,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            IconButton(onClick = { calendarViewModel.nextWeek() }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_media_next),
                    contentDescription = "Next week"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            calendarViewModel.daysOfWeek.forEach { dayOfWeek ->
                Text(
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(days) { day ->
                DayView(
                    day = day,
                    isSelected = day == selectedDay,
                    isToday = day == LocalDate.now(),
                    onClick = {
                        calendarViewModel.onDaySelected(day)
                        onDaySelected(day)
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayView(day: LocalDate, isSelected: Boolean, isToday: Boolean, onClick: () -> Unit) {
    val backgroundColor = when {
        isSelected -> Color.DarkGray
        isToday -> Color.LightGray
        else -> Color.Transparent
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .background(backgroundColor)
            .clickable { onClick() }
    ) {
        Text(
            text = day.dayOfMonth.toString(),
            color = if (isSelected) Color.White else Color.Black
        )
    }
}