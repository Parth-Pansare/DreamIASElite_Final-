package com.app.dreamiaselite.ui.screen.screens.help

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun HelpFeedbackScreen(
    currentUserEmail: String?
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Help",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "Get quick answers to common questions and helpful tips to make the most of your study sessions.",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Quick help",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = "• If tests are not loading, check your internet connection.\n" +
                            "• For login issues, make sure you’re using the latest app version.\n" +
                            "• You can clear app data & re-login if content looks outdated.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                )
            }
        }

        Text(
            text = "Frequently Asked Questions",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 8.dp)
        )

        FaqItem(
            question = "How can I access tests?",
            answer = "Navigate to the 'Tests' tab from the bottom navigation bar to find subject-wise reference books and full-length tests."
        )

        FaqItem(
            question = "How do I set study goals?",
            answer = "Open the drawer and select 'Study Planner'. Here you can add daily tasks and mark them as done as you progress."
        )

        FaqItem(
            question = "Where can I find Previous Year Questions?",
            answer = "Go to the 'PYQ' tab in the bottom bar. You can explore papers year-wise or subject-wise with detailed explanations."
        )

        FaqItem(
            question = "Can I change the app theme?",
            answer = "Yes! In the navigation drawer, select 'Theme & Appearance' to switch between light and dark modes and choose your favorite accent color."
        )

        FaqItem(
            question = "How do I update my profile?",
            answer = "Open the drawer and tap on 'My Profile'. You can edit your name, target year, and update your profile photo there."
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun FaqItem(question: String, answer: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = answer,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            )
        }
    }
}

