package com.app.dreamiaselite.ui.screen.screens.pyq

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import android.net.Uri
import kotlinx.coroutines.delay

private data class PyqPaper(
    val id: String,
    val title: String,
    val questions: String,
    val statusLabel: String? = null,
    val statusColor: Color = Color(0xFF2ECF7C),
    val score: Int? = null,
    val icon: ImageVector,
    val iconTint: Color,
    val iconBackground: Color,
    val onAttempt: () -> Unit = {}
)

private data class PyqQuestion(
    val id: String,
    val year: String,
    val paper: String,
    val subject: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

private data class PyqTestResult(
    val paperId: String,
    val selected: List<Int?>
)

@Composable
fun PyqScreen(navController: NavController) {

    val tabs = listOf("Year-wise", "Subject-wise")
    val selectedTab = remember { mutableStateOf(tabs.first()) }
    val years = listOf("2024", "2023", "2022", "2021", "2020")
    val selectedYear = remember { mutableStateOf("2023") }
    val subjects = listOf("GS Paper 1", "GS Paper 2", "GS Paper 3", "CSAT")
    val selectedSubject = remember { mutableStateOf(subjects.first()) }

    val pyqQuestions = remember { providePyqQuestions() }
    val basePapers = remember { providePyqPapers() }
    val papers = basePapers.map { paper ->
        paper.copy(
            onAttempt = {
                navController.navigate("pyq_test/${paper.id}")
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        item {
            Text(
                text = "Previous Year Questions",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        item {
            FilterTabs(
                tabs = tabs,
                selectedTab = selectedTab.value,
                onTabSelected = { selectedTab.value = it }
            )
        }

        item {
            if (selectedTab.value == "Year-wise") {
                YearSelector(years, selectedYear.value) { selectedYear.value = it }
            } else {
                SubjectSelector(subjects, selectedSubject.value) { selectedSubject.value = it }
            }
        }

        item { AttemptSummaryCard() }

        item {
            Text(
                text = if (selectedTab.value == "Year-wise") "${selectedYear.value} Papers" else "${selectedSubject.value} Papers",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(papers) { paper ->
            PyqPaperCard(paper)
        }

        item { StatsRow() }

        item { StudyTipCard() }
    }
}

// ----------------- Paper Detail -----------------

@Composable
fun PyqPaperDetailScreen(navController: NavController, paperId: String) {
    val papers = remember { providePyqPapers() }
    val questions = remember { providePyqQuestions() }
    val paper = papers.find { it.id == paperId }
    val filteredQuestions = questions.filter { it.paper.equals(paper?.title, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (paper == null) {
            Text(
                text = "Paper not found.",
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)
            )
            return@Column
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = paper.title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = paper.questions,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                )
            }
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = paper.iconBackground
            ) {
                Icon(
                    imageVector = paper.icon,
                    contentDescription = paper.title,
                    tint = paper.iconTint,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }

        Divider(color = Color(0xFFE0E4EC))

        filteredQuestions.forEachIndexed { index, q ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Q${index + 1}. ${q.question}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                q.options.forEachIndexed { optIndex, option ->
                    val isCorrect = optIndex == q.correctIndex
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (isCorrect) Color(0xFFEAF7EF) else Color.Transparent
                            )
                            .padding(horizontal = 4.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ('A' + optIndex).toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = if (isCorrect) Color(0xFF1B6B35) else MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Text(
                    text = "Correct: ${q.options[q.correctIndex]}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1B6B35)
                    )
                )
                Text(
                    text = "Explanation: ${q.explanation}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                )
            }
            if (index < filteredQuestions.lastIndex) {
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            }
        }
    }
}

// ----------------- PYQ Test (MCQ) -----------------

@Composable
fun PyqTestScreen(navController: NavHostController, paperId: String) {
    val papers = remember { providePyqPapers() }
    val questions = remember { providePyqQuestions() }
    val paper = papers.find { it.id == paperId }
    val filteredQuestions = remember(paperId) {
        questions.filter { it.paper.equals(paper?.title, ignoreCase = true) }.take(10)
    }

    if (paper == null || filteredQuestions.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No PYQ test available.", color = MaterialTheme.colorScheme.onSurface)
        }
        return
    }

    val total = filteredQuestions.size
    var current by remember { mutableStateOf(0) }
    var selected by remember { mutableStateOf(List(total) { null as Int? }) }
    var showSubmitConfirm by remember { mutableStateOf(false to 0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = paper.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = "Question ${current + 1} of $total",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    )
                }
                AssistChip(
                    onClick = {},
                    label = { Text("${current + 1} / $total") },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                        labelColor = MaterialTheme.colorScheme.primary
                    )
                )
            }

            val q = filteredQuestions[current]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = q.question,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                q.options.forEachIndexed { idx, option ->
                    val isSelected = selected[current] == idx
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.08f) else Color.Transparent)
                            .clickable {
                                selected = selected.toMutableList().also {
                                    it[current] = if (it[current] == idx) null else idx
                                }
                            }
                            .padding(horizontal = 4.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ('A' + idx).toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    if (idx < q.options.lastIndex) {
                        Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), thickness = 0.5.dp)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                enabled = current > 0,
                onClick = { current-- },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) { Text("Previous") }

            Button(
                onClick = {
                    if (current < total - 1) {
                        current++
                    } else {
                        val answered = selected.count { it != null }
                        showSubmitConfirm = Pair(true, answered)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(if (current < total - 1) "Next" else "Submit", color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        val (confirmVisible, answeredCount) = showSubmitConfirm
        if (confirmVisible) {
            AlertDialog(
                onDismissRequest = { showSubmitConfirm = false to 0 },
                confirmButton = {
                    Button(
                        onClick = {
                            val selectedString = selected.joinToString(",") { it?.toString() ?: "-1" }
                            val encodedSelected = Uri.encode(selectedString)
                            showSubmitConfirm = false to 0
                            navController.navigate("pyq_test_result/$paperId?selected=$encodedSelected") {
                                navController.currentDestination?.id?.let { popUpTo(it) { inclusive = true } }
                                launchSingleTop = true
                            }
                        }
                    ) { Text("Submit now") }
                },
                dismissButton = {
                    Button(
                        onClick = { showSubmitConfirm = false to 0 },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) { Text("Continue test", color = MaterialTheme.colorScheme.onSurface) }
                },
                title = { Text("Submit test?") },
                text = {
                    Text(
                        "Answered $answeredCount of $total. Submit now?",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            )
        }
    }
}

@Composable
fun PyqTestResultScreen(navController: NavHostController, paperId: String, selectedString: String?) {
    val paperTitle = remember(paperId) {
        providePyqPapers().find { it.id == paperId }?.title
    }
    val selected: List<Int?> = selectedString
        ?.split(",")
        ?.map { it.toIntOrNull()?.takeIf { v -> v >= 0 } }
        ?: emptyList()

    val questions = remember { providePyqQuestions() }
    val filtered = questions.filter { it.paper.equals(paperTitle, ignoreCase = true) }
        .take(10)

    val popToPyq: () -> Unit = {
        navController.popBackStack("pyq", inclusive = false)
    }

    BackHandler { popToPyq() }

    if (filtered.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No result to show.", color = MaterialTheme.colorScheme.onSurface)
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "PYQ Test Review",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        filtered.forEachIndexed { index, q ->
            val user = selected.getOrNull(index)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Q${index + 1}. ${q.question}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                q.options.forEachIndexed { optIndex, option ->
                    val isCorrect = optIndex == q.correctIndex
                    val isUser = optIndex == user
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                when {
                                    isCorrect -> Color(0xFFEAF7EF)
                                    isUser -> MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                                    else -> Color.Transparent
                                }
                            )
                            .padding(horizontal = 4.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ('A' + optIndex).toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = when {
                                    isCorrect -> Color(0xFF1B6B35)
                                    isUser -> MaterialTheme.colorScheme.primary
                                    else -> MaterialTheme.colorScheme.onSurface
                                }
                            )
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Text(
                    text = "Correct: ${q.options[q.correctIndex]}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1B6B35)
                    )
                )
                Text(
                    text = "Explanation: ${q.explanation}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                )
            }
            if (index < filtered.lastIndex) {
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { popToPyq() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Back to PYQs", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}


@Composable
private fun FilterTabs(tabs: List<String>, selectedTab: String, onTabSelected: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            tabs.forEach { tab ->
                val isSelected = tab == selectedTab

                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) Color(0xFF2E6BFF) else Color.Transparent
                ) {
                    Box(
                        modifier = Modifier
                            .clickableWithoutRipple { onTabSelected(tab) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                    Text(
                        text = tab,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    )
                }
                }
            }
        }
    }
}

@Composable
private fun YearSelector(years: List<String>, selectedYear: String, onSelect: (String) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        years.forEach { year ->
            val isSelected = year == selectedYear

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) Color(0xFFE8ECFF) else MaterialTheme.colorScheme.surface,
                border = if (!isSelected) BorderStroke(1.dp, Color(0xFFE3E6EC)) else null
            ) {
                Box(
                    modifier = Modifier
                        .clickableWithoutRipple { onSelect(year) }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = year,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (isSelected) Color(0xFF2E6BFF) else MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun SubjectSelector(subjects: List<String>, selectedSubject: String, onSelect: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        subjects.forEach { subject ->
            val isSelected = subject == selectedSubject
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) Color(0xFFE8ECFF) else MaterialTheme.colorScheme.surface,
                border = if (!isSelected) BorderStroke(1.dp, Color(0xFFE3E6EC)) else null
            ) {
                Box(
                    modifier = Modifier
                        .clickableWithoutRipple { onSelect(subject) }
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = subject,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            color = if (isSelected) Color(0xFF2E6BFF) else MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }
    }
}


@Composable
private fun AttemptSummaryCard() {

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF9C1BEA), Color(0xFF6C1BCE))
                    )
                )
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {
                        Text(
                            text = "Papers Attempted",
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text(
                            text = "5 / 15",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Surface(
                        modifier = Modifier.size(42.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White.copy(alpha = 0.18f)
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Icon(
                                imageVector = Icons.Outlined.CloudDownload,
                                contentDescription = "download",
                                tint = Color.White
                            )
                        }
                    }
                }

                LinearProgressIndicator(
                    progress = 5f / 15f,
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.25f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Text(
                    text = "Keep solving! PYQs are the key to success 🎯",
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}


@Composable
private fun PyqPaperCard(paper: PyqPaper) {

   Card(
       modifier = Modifier.fillMaxWidth(),
       shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(paper.iconBackground),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = paper.icon,
                            contentDescription = paper.title,
                            tint = paper.iconTint,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                       Text(
                           text = paper.title,
                           style = MaterialTheme.typography.titleMedium.copy(
                               fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = paper.questions,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }

                paper.statusLabel?.let {
                    StatusBadge(label = it, color = paper.statusColor)
                }
            }

            paper.score?.let { score ->
                ScoreStrip(score)
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = paper.onAttempt,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF111827),
                    contentColor = Color.White
                )
            ) {
                Text("Attempt now")
            }
        }
    }
}

@Composable
private fun StatusBadge(label: String, color: Color) {

    Surface(
        color = color.copy(alpha = 0.14f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = label,
            color = color,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}


@Composable
private fun ScoreStrip(score: Int) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF0F9F0)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text("Your Score", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            Text("$score%", color = Color(0xFF17B26A), fontWeight = FontWeight.Bold)
        }
    }
}



@Composable
private fun StatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatsCard(
            label = "Latest Year Papers",
            value = "5",
            icon = Icons.Outlined.Book,
            tint = Color(0xFF2E6BFF),
            bubble = Color(0xFFE8EDFF),
            modifier = Modifier.weight(1f)
        )
        StatsCard(
            label = "Completed Papers",
            value = "5",
            icon = Icons.Outlined.PlayCircle,
            tint = Color(0xFF1BA97F),
            bubble = Color(0xFFE8F6F1),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatsCard(
    label: String,
    value: String,
    icon: ImageVector,
    tint: Color,
    bubble: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(bubble),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = tint
                )
            }
           Text(
               text = value,
               style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            )
        }
    }
}


@Composable
private fun StudyTipCard() {

    val tips = listOf(
        "Analyze the pattern of questions asked in previous years. Focus on frequently asked topics and practice at least 2–3 papers from each subject.",
        "Simulate exam conditions: 2-hour timer, OMR-style marking, and post-test error log to spot recurring mistakes.",
        "Group PYQs by themes (polity, geography, environment) and revise with concise notes before attempting fresh mocks."
    )
    val tipColors = listOf(
        Color(0xFFFF6F1E),
        Color(0xFF2E6BFF),
        Color(0xFF15803D)
    )
    var tipIndex by remember { mutableStateOf(0) }
    var dragDistance by remember { mutableStateOf(0f) }
    val cardColor by animateColorAsState(
        targetValue = tipColors[tipIndex % tipColors.size],
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 650),
        label = "tip_card_color"
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(20_000)
            tipIndex = (tipIndex + 1) % tips.size
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Tip info",
                tint = Color.White,
                modifier = Modifier.align(Alignment.TopEnd)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(tips.size, tipIndex) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                when {
                                    dragDistance > 60 -> tipIndex = (tipIndex - 1 + tips.size) % tips.size
                                    dragDistance < -60 -> tipIndex = (tipIndex + 1) % tips.size
                                }
                                dragDistance = 0f
                            }
                        ) { change, dragAmount ->
                            dragDistance += dragAmount
                            change.consume()
                        }
                    }
            ) {

                Text(
                    text = "Study Tip",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Text(
                    text = tips[tipIndex],
                    color = Color.White,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

private fun providePyqPapers(): List<PyqPaper> = listOf(
    PyqPaper(
        id = "gs_paper_1",
        title = "GS Paper 1",
        questions = "100 Questions",
        statusLabel = "Downloaded",
        score = 68,
        icon = Icons.Outlined.CheckCircle,
        iconTint = Color(0xFF28B463),
        iconBackground = Color(0xFFE8F7EF)
    ),
    PyqPaper(
        id = "gs_paper_2",
        title = "GS Paper 2",
        questions = "100 Questions",
        statusLabel = "Downloaded",
        score = null,
        icon = Icons.Outlined.Quiz,
        iconTint = Color(0xFF2E86DE),
        iconBackground = Color(0xFFE8F1FF)
    ),
    PyqPaper(
        id = "gs_paper_3",
        title = "GS Paper 3",
        questions = "100 Questions",
        statusLabel = null,
        score = null,
        icon = Icons.Outlined.Assignment,
        iconTint = Color(0xFF8E44AD),
        iconBackground = Color(0xFFF3E9FF)
    ),
    PyqPaper(
        id = "csat",
        title = "CSAT",
        questions = "80 Questions",
        statusLabel = "Downloaded",
        score = 72,
        icon = Icons.Outlined.WorkspacePremium,
        iconTint = Color(0xFF16A085),
        iconBackground = Color(0xFFE6FAF3)
    )
)

private fun providePyqQuestions(): List<PyqQuestion> {
    fun mcq(
        idSuffix: Int,
        year: String,
        paper: String,
        subject: String,
        stem: String,
        options: List<String>,
        correct: Int,
        explanation: String
    ) = PyqQuestion(
        id = "${year}_${paper}_${idSuffix}",
        year = year,
        paper = paper,
        subject = subject,
        question = stem,
        options = options,
        correctIndex = correct,
        explanation = explanation
    )

    val gs1 = List(10) { idx ->
        mcq(
            idSuffix = idx + 1,
            year = "2024",
            paper = "GS Paper 1",
            subject = if (idx % 2 == 0) "History" else "Geography",
            stem = when (idx) {
                0 -> "Immediate cause for launching the Non-Cooperation Movement (1920) was:"
                1 -> "Tropical cyclones are rare in the South Atlantic because:"
                2 -> "Gupta era is associated with:"
                3 -> "The ITCZ shifts north over India in July mainly due to:"
                4 -> "Ajanta paintings primarily depict:"
                5 -> "Pallava architecture is best exemplified by:"
                6 -> "Rock-cut caves at Elephanta are dedicated to:"
                7 -> "The Khilafat Movement leaders were:"
                8 -> "Chola naval expeditions reached:"
                else -> "India’s monsoon trough in July lies:"
            },
            options = when (idx) {
                0 -> listOf("Failure of Khilafat talks", "Economic depression", "Rowlatt Act & Jallianwala Bagh", "Arrest of Congress leaders")
                1 -> listOf("Warm SST & strong shear", "Cool SST & strong shear", "Strong Coriolis & high humidity", "Frequent ENSO stability")
                2 -> listOf("Decimal system & Ajanta murals", "Iron pillar & chaityas", "Vedic rituals", "Greek coinage")
                3 -> listOf("Southern Hemisphere heating", "Northern landmass heating", "ITCZ anchored at equator", "High Antarctic pressure")
                4 -> listOf("Buddha’s life events", "Bhakti saints", "Gupta kings", "Delhi Sultanate")
                5 -> listOf("Lingaraja temple", "Shore & Kailasanatha temples", "Sun temple Modhera", "Dilwara temples")
                6 -> listOf("Vishnu", "Shiva", "Buddha", "Jaina Tirthankaras")
                7 -> listOf("Ali brothers", "Tilak and Lajpat Rai", "Nehru and Bose", "Gokhale and Ranade")
                8 -> listOf("Persian Gulf", "Sri Lanka", "Southeast Asia", "East Africa only")
                else -> listOf("Along 20°S", "South of equator", "Across the Indo-Gangetic plains", "Over Arabian Sea")
            },
            correct = when (idx) {
                0 -> 2
                1 -> 1
                2 -> 0
                3 -> 1
                4 -> 0
                5 -> 1
                6 -> 1
                7 -> 0
                8 -> 2
                else -> 2
            },
            explanation = "PYQ-style MCQ for practice."
        )
    }

    val gs2 = List(10) { idx ->
        mcq(
            idSuffix = idx + 1,
            year = "2023",
            paper = "GS Paper 2",
            subject = if (idx % 2 == 0) "Polity" else "Governance",
            stem = when (idx) {
                0 -> "102nd Constitutional Amendment primarily:"
                1 -> "MSPs are recommended by:"
                2 -> "Money Bill can be introduced in:"
                3 -> "8th Schedule relates to:"
                4 -> "Tenth Schedule deals with:"
                5 -> "NCBC got constitutional status via:"
                6 -> "Article 368 refers to:"
                7 -> "CAG is appointed by:"
                8 -> "Minimum age for Rajya Sabha member:"
                else -> "Anti-defection applies to:"
            },
            options = listOf(
                "Constitutional status to NCBC",
                "NITI Aayog",
                "Lok Sabha only",
                "Languages",
                "Anti-defection",
                "102nd Amendment",
                "Constitutional amendment procedure",
                "President",
                "30 years",
                "Members switching parties"
            ),
            correct = when (idx) {
                0 -> 0
                1 -> 1
                2 -> 2
                3 -> 3
                4 -> 4
                5 -> 5
                6 -> 6
                7 -> 7
                8 -> 8
                else -> 9
            },
            explanation = "Core polity/governance factual MCQ."
        )
    }

    val gs3 = List(10) { idx ->
        mcq(
            idSuffix = idx + 1,
            year = "2022",
            paper = "GS Paper 3",
            subject = "Economy & Environment",
            stem = when (idx) {
                0 -> "MSP primarily aims to:"
                1 -> "Repo rate is:"
                2 -> "GST is a:"
                3 -> "IIP is published by:"
                4 -> "FRBM stands for:"
                5 -> "Balance of Payments includes:"
                6 -> "Montreal Protocol targets:"
                7 -> "Paris Agreement aims to limit warming:"
                8 -> "CITES regulates:"
                else -> "Ramsar sites refer to:"
            },
            options = when (idx) {
                0 -> listOf("Export competitiveness", "Price assurance to farmers", "Reduce subsidy", "Promote coarse cereals")
                1 -> listOf("Rate banks lend to RBI", "Rate RBI lends to banks", "Rate for deposits", "Rate for savings")
                2 -> listOf("Direct tax", "Destination-based indirect tax", "Origin-based tax", "Wealth tax")
                3 -> listOf("NITI", "RBI", "NSO", "SEBI")
                4 -> listOf("Fiscal Responsibility and Budget Management", "Financial Reform Board Mandate", "Fiscal Reserve Benchmark", "Federal Resource Budgeting")
                5 -> listOf("Only current account", "Only capital account", "Both current and capital", "Only FX reserves")
                6 -> listOf("GHGs", "ODS", "Marine pollution", "POPs")
                7 -> listOf("Below 3°C", "Below 2°C and pursue 1.5°C", "Equal cuts for all", "No targets")
                8 -> listOf("Endangered species trade", "Ozone layer", "Carbon markets", "Ship emissions")
                else -> listOf("Wetlands", "Forests", "Deserts", "Mountains")
            },
            correct = when (idx) {
                0 -> 1
                1 -> 1
                2 -> 1
                3 -> 2
                4 -> 0
                5 -> 2
                6 -> 1
                7 -> 1
                8 -> 0
                else -> 0
            },
            explanation = "Key economy/environment objective MCQ."
        )
    }

    val csat = List(10) { idx ->
        mcq(
            idSuffix = idx + 1,
            year = "2021",
            paper = "CSAT",
            subject = "Reasoning",
            stem = when (idx) {
                0 -> "A is thrice as efficient as B; together finish in 18 days. B alone takes:"
                1 -> "Speed-time-distance basic relation:"
                2 -> "If profit is ₹20 on cost ₹100, profit % is:"
                3 -> "Average of first ten natural numbers is:"
                4 -> "If SP = CP, then profit/loss is:"
                5 -> "Two trains 60 km/h and 40 km/h opposite directions, relative speed:"
                6 -> "Simple interest at 10% on ₹1000 for 2 years:"
                7 -> "Ratio 2:3 equals fraction:"
                8 -> "If a:b=2:5 and b:c=3:4, a:c ="
                else -> "If x+y=10 and x−y=2, x="
            },
            options = when (idx) {
                0 -> listOf("54 days", "72 days", "90 days", "108 days")
                1 -> listOf("S = D/T", "T = S/D", "D = S×T", "All of these")
                2 -> listOf("10%", "15%", "20%", "25%")
                3 -> listOf("4.5", "5.5", "6", "6.5")
                4 -> listOf("Profit", "Loss", "Break-even", "Cannot say")
                5 -> listOf("20 km/h", "40 km/h", "60 km/h", "100 km/h")
                6 -> listOf("₹100", "₹150", "₹200", "₹250")
                7 -> listOf("2/5", "3/5", "2/3", "3/2")
                8 -> listOf("2:3", "6:20", "6:10", "15:8")
                else -> listOf("4", "6", "8", "10")
            },
            correct = when (idx) {
                0 -> 1
                1 -> 3
                2 -> 2
                3 -> 1
                4 -> 2
                5 -> 3
                6 -> 2
                7 -> 0
                8 -> 3
                else -> 1
            },
            explanation = "Basic CSAT arithmetic/logic practice."
        )
    }

    return gs1 + gs2 + gs3 + csat
}


private fun Modifier.clickableWithoutRipple(onClick: () -> Unit): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
}
