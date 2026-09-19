package com.jerinjoseph.focus_plan_builder.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jerinjoseph.focus_plan_builder.R

@Composable
fun FocusPlanForm(subject: String,
                  minutesText: String,
                  onSubjectChange: (String) -> Unit,
                  onMinutesChange: (String) -> Unit,
                  isValidMinutes: Boolean,
                  canCreatePlan: Boolean,
                  onCreatePlan: () -> Unit,
                  modifier: Modifier = Modifier) {
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

        if (!isValidMinutes && minutesText.isNotBlank()) {
            Text(
                text = stringResource(R.string.session_duration_error),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onCreatePlan,
            modifier = Modifier.fillMaxWidth(),
            enabled = canCreatePlan
        ) {
            Text(stringResource(R.string.create_plan_button))
        }
    }
}