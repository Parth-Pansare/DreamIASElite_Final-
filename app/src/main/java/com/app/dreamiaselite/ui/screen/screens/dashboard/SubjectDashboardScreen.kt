package com.app.dreamiaselite.ui.screens.dashboard

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.dreamiaselite.ui.theme.AccentCyan
import com.app.dreamiaselite.ui.theme.AccentLavender
import com.app.dreamiaselite.ui.theme.AccentPeach

data class SubjectCardItem(
    val title: String,
    val subtitle: String,
    val meta: String,
    val articleId: Int? = null
)

@Composable
fun SubjectDashboardScreen(subject: String, navController: NavHostController) {

    val dailyMcqs = remember(subject) {
        listOf(
            SubjectCardItem("10 MCQ Warm-up", "Mixed difficulty", "Auto-filtered to $subject"),
            SubjectCardItem("10 MCQ Timed Drill", "5 mins • speed focus", "Exactly 10 questions")
        )
    }

    val caArticles = remember(subject) {
        buildList {
            add(SubjectCardItem("Key $subject CA digest", "3 articles", "Updated today", articleId = 1))
            add(SubjectCardItem("Exam-oriented briefs", "2 mins each", "Flag for revision", articleId = 2))
            repeat(9) { idx ->
                add(
                    SubjectCardItem(
                        "$subject current note ${idx + 1}",
                        "Quick brief ${idx + 1}",
                        "New in the last week"
                    )
                )
            }
        }
    }

    val notes = remember(subject) {
        buildList {
            add(SubjectCardItem("Last opened note", "Annotations saved", "Resume"))
            add(SubjectCardItem("Flashcards", "Key terms & dates", "Review 5 cards"))
            repeat(9) { idx ->
                add(
                    SubjectCardItem(
                        "$subject note pack ${idx + 1}",
                        "Concise bullets",
                        "Ready to revise"
                    )
                )
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surface
                    )
                )
            ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { navController.popBackStack() }
                        .padding(6.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Column {
                    Text(
                        text = "$subject Dashboard",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = "Everything auto-filtered to $subject",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    )
                }
            }
        }

        item { AccuracyCard() }

        item {
            SubjectSection(
                title = "Daily MCQs",
                caption = "Short drills to stay sharp",
                pillColor = AccentCyan,
                items = dailyMcqs,
                onCardClick = { item ->
                    val encodedTitle = Uri.encode("$subject - ${item.title}")
                    val originRoute = Uri.encode("subject_dashboard/${Uri.encode(subject)}")
                    navController.navigate("test_session/$encodedTitle?origin=$originRoute&originType=subject&originSubject=${Uri.encode(subject)}")
                }
            )
        }

        item {
            SubjectSection(
                title = "Important CA articles",
                caption = "$subject-linked current affairs",
                pillColor = AccentLavender,
                items = caArticles,
                onCardClick = { item ->
                    item.articleId?.let { id ->
                        navController.navigate("current_affair_detail/$id")
                    }
                }
            )
        }

        item {
            SubjectSection(
                title = "Notes",
                caption = "Your saved material",
                pillColor = AccentPeach,
                items = notes,
                onCardClick = { item ->
                    when {
                        item.title.startsWith("Last opened note", ignoreCase = true) -> {
                            val encoded = Uri.encode(subject)
                            navController.navigate("notes_last_opened/$encoded")
                        }
                        item.title.startsWith("Flashcards", ignoreCase = true) -> {
                            val encoded = Uri.encode(subject)
                            navController.navigate("notes_flashcards/$encoded")
                        }
                    }
                }
            )
        }

        item { Spacer(Modifier.height(6.dp)) }
    }
}

@Composable
private fun SubjectSection(
    title: String,
    caption: String,
    pillColor: Color,
    items: List<SubjectCardItem>,
    onCardClick: ((SubjectCardItem) -> Unit)? = null
) {
    val visibleItems = items

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SectionHeader(
            title = title,
            caption = caption,
            pillColor = pillColor,
            viewButtonLabel = null,
            onViewAction = {}
        )

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            visibleItems.take(2).forEach { item ->
                SubjectCard(
                    item = item,
                    highlightColor = pillColor,
                    onClick = onCardClick?.let { handler -> { handler(item) } }
                )
            }
        }
    }
}

@Composable
private fun SubjectCard(
    item: SubjectCardItem,
    highlightColor: Color,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(
                            highlightColor.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            )
            Text(
                text = item.meta,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = highlightColor,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Composable
private fun AccuracyCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Accuracy & Progress",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = "Accuracy 68% • Attempts 120 • Streak 3 days",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            )
            Text(
                text = "Weak spots: Important Acts, Modern basics",
                style = MaterialTheme.typography.bodySmall.copy(color = AccentPeach)
            )
        }
    }
}
