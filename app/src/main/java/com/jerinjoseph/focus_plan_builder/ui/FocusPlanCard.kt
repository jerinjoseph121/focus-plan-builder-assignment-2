package com.jerinjoseph.focus_plan_builder.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jerinjoseph.focus_plan_builder.R
import com.jerinjoseph.focus_plan_builder.model.FocusPlan

@Composable
fun FocusPlanCard(plan: FocusPlan?,
                  isDisplayCard: Boolean,
                  modifier: Modifier = Modifier) {
    if (isDisplayCard) {
        Card(modifier = modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = stringResource(R.string.focus_plan_card_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = plan?.subject ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = stringResource(R.string.focus_plan_duration_label, plan?.minutes.toString()),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = stringResource(R.string.focus_plan_category_label, plan?.category ?: ""),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = stringResource(R.string.focus_plan_break_minutes_label, plan?.breakMinutes.toString()),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = stringResource(
                        R.string.focus_plan_card_summary,
                        plan?.subject.toString(),
                        plan?.minutes.toString(),
                        plan?.breakMinutes.toString()
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}