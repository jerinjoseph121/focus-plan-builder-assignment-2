package com.jerinjoseph.focus_plan_builder.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jerinjoseph.focus_plan_builder.R
import com.jerinjoseph.focus_plan_builder.model.FocusPlan
import com.jerinjoseph.focus_plan_builder.ui.theme.Focus_Plan_BuilderTheme

@Composable
fun FocusPlanRoute(modifier: Modifier = Modifier) {
    var subject by rememberSaveable {
        mutableStateOf("")
    }

    var minutesText by rememberSaveable {
        mutableStateOf("")
    }

    var focusPlan by rememberSaveable {
        mutableStateOf(null as FocusPlan?)
    }

    var isDisplayCard by rememberSaveable {
        mutableStateOf(false)
    }

    val minutes: Int? = minutesText.toIntOrNull()

    val isValidSubject = subject.isNotBlank()

    val isValidMinutes = minutes != null && minutes in 10..180

    val canCreatePlan = isValidSubject && isValidMinutes

    FocusPlanScreen(
        subject = subject,
        minutesText = minutesText,
        plan = focusPlan,
        onSubjectChange = {
            subject = it
            isDisplayCard = false
        },

        onMinutesChange = {
            minutesText = it
            isDisplayCard = false
        },
        isValidMinutes = isValidMinutes,
        canCreatePlan = canCreatePlan,
        onCreatePlan = {
            val category = durationCategory(minutes ?: 0)
            val breakMinutes = recommendedBreak(minutes ?: 0)
            focusPlan = FocusPlan(subject, minutes, category, breakMinutes)
            isDisplayCard = true
        },
        isDisplayCard = isDisplayCard,
        modifier = modifier
    )
}

@Composable
fun FocusPlanScreen(subject: String,
                    minutesText: String,
                    plan: FocusPlan?,
                    onSubjectChange: (String) -> Unit,
                    onMinutesChange: (String) -> Unit,
                    isValidMinutes: Boolean,
                    canCreatePlan: Boolean,
                    onCreatePlan: () -> Unit,
                    isDisplayCard: Boolean,
                    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    )  {
        Text(
            text = stringResource(R.string.app_title),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        FocusPlanForm(
            subject = subject,
            minutesText = minutesText,
            onSubjectChange = onSubjectChange,
            onMinutesChange = onMinutesChange,
            isValidMinutes = isValidMinutes,
            canCreatePlan = canCreatePlan,
            onCreatePlan = onCreatePlan,
            modifier = modifier
        )

        Spacer(modifier = Modifier.height(12.dp))

        FocusPlanCard(
            plan = plan,
            isDisplayCard = isDisplayCard,
            modifier = modifier
        )
    }
}

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

@Preview(showBackground = true)
@Composable
fun FocusPlanScreenPreview() {
    Focus_Plan_BuilderTheme {
        FocusPlanRoute()
    }
}

