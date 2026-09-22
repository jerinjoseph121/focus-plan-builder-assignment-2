package com.jerinjoseph.focus_plan_builder.model

data class FocusPlan(
    val subject: String,
    val minutes: Int,
    val category: String,
    val breakMinutes: Int
)

fun durationCategory(minutes: Int): String = when {
    minutes < 10 -> "Invalid"
    minutes in 10..29 -> "Quick review"
    minutes in 30..60 -> "Focused session"
    else -> "Extended session"
}

fun recommendedBreak(minutes: Int): Int = when (minutes) {
    in 10..29 -> 5
    in 30..60 -> 10
    else -> 15
}