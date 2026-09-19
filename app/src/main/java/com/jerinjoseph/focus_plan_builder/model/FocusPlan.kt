package com.jerinjoseph.focus_plan_builder.model

data class FocusPlan(
    val subject: String,
    val minutes: Int?,
    val category: String,
    val breakMinutes: Int
)