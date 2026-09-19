package com.jerinjoseph.focus_plan_builder.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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

    val minutes: Int? = minutesText.toIntOrNull()

    val canCreatePlan =
        subject.isNotBlank() &&
                minutes != null &&
                minutes in 10..180

    FocusPlanScreen(
        subject = subject,
        minutesText = minutesText,
        plan = focusPlan,
        onSubjectChange = { subject = it },
        onMinutesChange = { minutesText = it },
        canCreatePlan = canCreatePlan,
        onCreatePlan = {
            val category = durationCategory(minutes ?: 0)
            val breakMinutes = recommendedBreak(minutes ?: 0)
            focusPlan = FocusPlan(subject, minutes, category, breakMinutes)
        },
        modifier = modifier
    )
}

@Composable
fun FocusPlanScreen(subject: String,
                    minutesText: String,
                    plan: FocusPlan?,
                    onSubjectChange: (String) -> Unit,
                    onMinutesChange: (String) -> Unit,
                    canCreatePlan: Boolean,
                    onCreatePlan: () -> Unit,
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

        Text(
            text = stringResource(R.string.app_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.study_subject_prompt),
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = subject,
                onValueChange = onSubjectChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.study_subject_label)) },
                placeholder = { Text(stringResource(R.string.study_subject_placeholder)) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.session_duration_prompt),
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = minutesText,
                onValueChange = onMinutesChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.session_duration_label)) },
                placeholder = { Text(stringResource(R.string.session_duration_placeholder)) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onCreatePlan,
                modifier = Modifier.fillMaxWidth(),
                enabled = canCreatePlan
            ) {
                Text(stringResource(R.string.create_plan_button))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(modifier = modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                }
            }
        }
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

