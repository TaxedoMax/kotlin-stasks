package com.sbook.kotlinstasks.entity

import java.time.LocalDate

data class Task(
    val id: Int,
    val name: String,
    val isDone: Boolean,
    val date: LocalDate,
    val position: Int
)