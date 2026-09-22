package com.jerinjoseph.focus_plan_builder.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jerinjoseph.focus_plan_builder.R
import com.jerinjoseph.focus_plan_builder.model.FocusPlan
import com.jerinjoseph.focus_plan_builder.model.durationCategory
import com.jerinjoseph.focus_plan_builder.model.recommendedBreak
import com.jerinjoseph.focus_plan_builder.ui.theme.Focus_Plan_BuilderTheme

@Composable
fun FocusPlanRoute(modifier: Modifier = Modifier) {
    var subject by rememberSaveable {
        mutableStateOf("")
    }

    var minutesText by rememberSaveable {
        mutableStateOf("")
    }

    var focusPlan by remember {
        mutableStateOf(null as FocusPlan?)
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
            focusPlan = null
        },

        onMinutesChange = {
            minutesText = it
            focusPlan = null
        },
        isValidMinutes = isValidMinutes,
        canCreatePlan = canCreatePlan,
        onCreatePlan = {
            if (minutes != null) {
                val category = durationCategory(minutes)
                val breakMinutes = recommendedBreak(minutes)
                focusPlan = FocusPlan(subject.trim(), minutes, category, breakMinutes)
            }
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
                    isValidMinutes: Boolean,
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

        FocusPlanForm(
            subject = subject,
            minutesText = minutesText,
            onSubjectChange = onSubjectChange,
            onMinutesChange = onMinutesChange,
            isValidMinutes = isValidMinutes,
            canCreatePlan = canCreatePlan,
            onCreatePlan = onCreatePlan,
            modifier = Modifier
        )

        if (plan != null) {
            FocusPlanCard(
                plan = plan,
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FocusPlanScreenPreview() {
    Focus_Plan_BuilderTheme {
        val focusPlan = FocusPlan("Android Development", 45, "Focused Session", 10)
        FocusPlanScreen(
            subject = focusPlan.subject,
            minutesText = focusPlan.minutes.toString(),
            plan = focusPlan,
            onSubjectChange = {},
            onMinutesChange = {},
            isValidMinutes = true,
            canCreatePlan = true,
            onCreatePlan = {}
        )
    }
}

