package com.app.dreamiaselite.ui.screens.currentaffairs
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

data class MonthlyCaTest(
    val id: Int,
    val title: String,
    val summary: String,
    val questions: Int,
    val durationMinutes: Int,
    val difficulty: String,
    val focus: List<String>,
    val lastUpdated: String
)

object MonthlyCaTestData {
    val tests = listOf(
        MonthlyCaTest(
            id = 101,
            title = "Monthly CA Test - November",
            summary = "10 MCQs covering GS1/GS2/GS3 current affairs with static linkages.",
            questions = 10,
            durationMinutes = 10,
            difficulty = "Moderate",
            focus = listOf("Polity rulings", "Economy data", "Environment treaties", "S&T missions"),
            lastUpdated = "Updated yesterday"
        ),
        MonthlyCaTest(
            id = 102,
            title = "Monthly CA Test - October",
            summary = "10 MCQs blend of static + current with emphasis on schemes and IR summits.",
            questions = 10,
            durationMinutes = 10,
            difficulty = "Adaptive",
            focus = listOf("Schemes", "Summits", "Reports", "Maps"),
            lastUpdated = "Updated last week"
        ),
        MonthlyCaTest(
            id = 103,
            title = "Monthly CA Test - September",
            summary = "10 MCQs fact + analysis mix to mirror UPSC prelims trickiness.",
            questions = 10,
            durationMinutes = 10,
            difficulty = "Moderate",
            focus = listOf("Bills & Acts", "Environment updates", "Economy trends"),
            lastUpdated = "Updated 2 weeks ago"
        ),
        MonthlyCaTest(
            id = 104,
            title = "Monthly CA Test - August",
            summary = "10 MCQs UPSC pattern with special stress on geography-linked CA.",
            questions = 10,
            durationMinutes = 10,
            difficulty = "Moderate",
            focus = listOf("Mapping", "Disaster mgmt", "International bodies"),
            lastUpdated = "Updated 3 weeks ago"
        ),
        MonthlyCaTest(
            id = 105,
            title = "Monthly CA Test - July",
            summary = "10 MCQs GS overlap focused set to sharpen CA recall + application.",
            questions = 10,
            durationMinutes = 10,
            difficulty = "Moderate",
            focus = listOf("Schemes", "Govt programmes", "Sci-Tech"),
            lastUpdated = "Updated last month"
        ),
        MonthlyCaTest(
            id = 106,
            title = "Monthly CA Test - June",
            summary = "10 MCQs mapping-heavy CA questions plus key environment updates.",
            questions = 10,
            durationMinutes = 10,
            difficulty = "Moderate",
            focus = listOf("Maps", "Conventions", "Indices"),
            lastUpdated = "Updated last month"
        )
    )

    fun getById(id: Int): MonthlyCaTest? = tests.find { it.id == id }
}

@Composable
fun MonthlyCaTestScreen(
    testId: Int,
    navController: NavHostController
    // Note: Start action can be wired to test engine later.
) {
    val test = remember(testId) { MonthlyCaTestData.getById(testId) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = test?.title ?: "Monthly CA Test",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Text(
            text = test?.summary ?: "Prepare with the latest current affairs test set.",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
            )
        )

        SummaryCard(test)
        FocusAreasCard(test)

        Button(
            onClick = {
                test?.let {
                    navController.navigate("monthly_ca_test_session/${it.id}")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = "Start Test",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
            )
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Back")
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun SummaryCard(test: MonthlyCaTest?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Test Overview",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        test?.let {
            MetaRow(icon = Icons.Outlined.Quiz, label = "${it.questions} questions")
            MetaRow(icon = Icons.Outlined.AccessTime, label = "${it.durationMinutes} min duration")
            MetaRow(icon = Icons.Outlined.BarChart, label = "Difficulty: ${it.difficulty}")
            MetaRow(icon = Icons.Outlined.Assessment, label = it.lastUpdated)
        } ?: Text(
            text = "We could not load this test. Please open it again.",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        )
    }
}

@Composable
private fun FocusAreasCard(test: MonthlyCaTest?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(Icons.Outlined.Bookmarks, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(
                text = "Focus Areas",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        test?.focus?.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                    )
                )
            }
        } ?: Text(
            text = "No focus areas listed for this test.",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        )
    }
}

@Composable
private fun MetaRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        )
    }
}
