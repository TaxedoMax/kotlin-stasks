package com.sbook.kotlinstasks.mainactivity.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.sbook.kotlinstasks.entity.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class TaskListViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    private val _filteredTasks = MutableStateFlow<List<Task>>(emptyList())
    val filteredTasks: StateFlow<List<Task>> = _filteredTasks

    private var selectedDate: LocalDate? = null

    private var taskList: MutableList<Task> = mutableListOf()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        taskList = (1..10).map {
            Task(it, "Task $it", false, LocalDate.now().plusDays(it % 3L), it)
        }.toMutableList()
        _tasks.value = taskList
        filterTasksByDate(LocalDate.now())
    }

    fun updateTask(task: Task) {
        taskList = taskList.map { if (it.id == task.id) task else it }.toMutableList()
        _tasks.value = taskList
    }

    fun filterTasksByDate(date: LocalDate) {
        selectedDate = date
        _filteredTasks.value = taskList.filter { it.date == date }
    }
}