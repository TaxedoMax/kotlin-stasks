package com.sbook.kotlinstasks.mainactivity.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel : ViewModel() {
    private val _selectedDay = MutableStateFlow<LocalDate?>(null)
    val selectedDay: StateFlow<LocalDate?> = _selectedDay

    private val _days = MutableStateFlow<List<LocalDate>>(emptyList())
    val days: StateFlow<List<LocalDate>> = _days

    private val _currentMonth = MutableStateFlow("")
    val currentMonth: StateFlow<String> = _currentMonth

    private var currentStartDate: LocalDate = LocalDate.now().with(DayOfWeek.MONDAY)

    val daysOfWeek = listOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY
    )

    init {
        updateDays()
    }

    fun onDaySelected(day: LocalDate) {
        _selectedDay.value = day
    }

    fun nextWeek() {
        currentStartDate = currentStartDate.plusWeeks(2)
        updateDays()
    }

    fun previousWeek() {
        currentStartDate = currentStartDate.minusWeeks(2)
        updateDays()
    }

    private fun updateDays() {
        val newDays = mutableListOf<LocalDate>()
        val startOfWeek = currentStartDate.with(DayOfWeek.MONDAY)
        for (i in 0 until 14) {
            newDays.add(startOfWeek.plusDays(i.toLong()))
        }
        _days.value = newDays

        _currentMonth.value = currentStartDate.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    }
}