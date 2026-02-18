package com.app.dreamiaselite.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.dreamiaselite.BottomNavItem
import com.app.dreamiaselite.ui.theme.*

//-------------------------------------------------------
// Data Model
//-------------------------------------------------------

data class HomeSectionItem(
    val title: String,
    val subtitle: String,
    val tag: String,
    val meta: String,
    val articleId: Int? = null,
    val testId: Int? = null
)

enum class DashboardSectionType(
    val key: String,
    val headerTitle: String,
    val caption: String,
    val highlightColor: Color
) {
    CurrentAffairs(
        key = "ca",
        headerTitle = "Monthly Current Affairs",
        caption = "Curated for UPSC CSE",
        highlightColor = AccentCyan
    ),
    CurrentAffairsTest(
        key = "test",
        headerTitle = "Monthly Current Affairs Test",
        caption = "Based on your preparation pattern",
        highlightColor = AccentLavender
    );

    companion object {
        fun fromKey(key: String?): DashboardSectionType? =
            values().firstOrNull { it.key == key }
    }
}

private val monthlyCurrentAffairsItems = listOf(
    HomeSectionItem(
        title = "RBI Monetary Policy Review",
        subtitle = "Economy • GS3",
        tag = "Current Affairs",
        meta = "Updated today",
        articleId = 1
    ),
    HomeSectionItem(
        title = "New Environment Treaty Signed",
        subtitle = "Environment • GS3",
        tag = "Current Affairs",
        meta = "Key facts & analysis",
        articleId = 2
    ),
    HomeSectionItem(
        title = "Supreme Court ruling on FRs",
        subtitle = "Polity • GS2",
        tag = "Current Affairs",
        meta = "Judgment highlights",
        articleId = 3
    ),
    HomeSectionItem(
        title = "India’s GDP forecast updated",
        subtitle = "Economy • GS3",
        tag = "Current Affairs",
        meta = "Data-driven brief",
        articleId = 4
    ),
    HomeSectionItem(
        title = "New MSP changes explained",
        subtitle = "Agriculture • GS3",
        tag = "Current Affairs",
        meta = "What to watch",
        articleId = 5
    ),
    HomeSectionItem(
        title = "Blue Economy push",
        subtitle = "Environment • GS3",
        tag = "Current Affairs",
        meta = "Schemes & targets",
        articleId = 6
    ),
    HomeSectionItem(
        title = "AI regulation paper",
        subtitle = "Sci-Tech • GS3",
        tag = "Current Affairs",
        meta = "Global snapshots",
        articleId = 7
    ),
    HomeSectionItem(
        title = "G20 outcomes tracker",
        subtitle = "IR • GS2",
        tag = "Current Affairs",
        meta = "Action points",
        articleId = 8
    ),
    HomeSectionItem(
        title = "Mission LiFE updates",
        subtitle = "Environment • GS3",
        tag = "Current Affairs",
        meta = "Targets & states",
        articleId = 9
    ),
    HomeSectionItem(
        title = "NATO expansion brief",
        subtitle = "IR • GS2",
        tag = "Current Affairs",
        meta = "Timeline & map",
        articleId = 10
    ),
    HomeSectionItem(
        title = "Quantum tech roadmap",
        subtitle = "Sci-Tech • GS3",
        tag = "Current Affairs",
        meta = "Key milestones",
        articleId = 11
    ),
    HomeSectionItem(
        title = "Forest (Amendment) Act",
        subtitle = "Environment • GS3",
        tag = "Current Affairs",
        meta = "Core provisions",
        articleId = 12
    )
)

private val monthlyCurrentAffairsTestItems = listOf(
    HomeSectionItem(
        title = "Monthly CA Test - November",
        subtitle = "50 Q • GS1/GS2/GS3 blend",
        tag = "Current Affairs Test",
        meta = "Updated yesterday",
        testId = 101
    ),
    HomeSectionItem(
        title = "Monthly CA Test - October",
        subtitle = "50 Q • Static + Current mix",
        tag = "Current Affairs Test",
        meta = "Adaptive scoring",
        testId = 102
    ),
    HomeSectionItem(
        title = "Monthly CA Test - September",
        subtitle = "40 Q • Facts + analysis",
        tag = "Current Affairs Test",
        meta = "Timed practice",
        testId = 103
    ),
    HomeSectionItem(
        title = "Monthly CA Test - August",
        subtitle = "40 Q • UPSC pattern",
        tag = "Current Affairs Test",
        meta = "Calibrated sets",
        testId = 104
    ),
    HomeSectionItem(
        title = "Monthly CA Test - July",
        subtitle = "35 Q • GS overlap",
        tag = "Current Affairs Test",
        meta = "Revision heavy",
        testId = 105
    ),
    HomeSectionItem(
        title = "Monthly CA Test - June",
        subtitle = "35 Q • Mapping + CA",
        tag = "Current Affairs Test",
        meta = "Mixed difficulty",
        testId = 106
    )
)

private fun itemsForSection(type: DashboardSectionType): List<HomeSectionItem> = when (type) {
    DashboardSectionType.CurrentAffairs -> monthlyCurrentAffairsItems
    DashboardSectionType.CurrentAffairsTest -> monthlyCurrentAffairsTestItems
}

private fun handleHomeItemNavigation(item: HomeSectionItem, navController: NavHostController) {
    item.articleId?.let { id ->
        navController.navigate("current_affair_detail/$id")
        return
    }
    item.testId?.let { id ->
        navController.navigate("monthly_ca_test/$id")
        return
    }
    navController.navigate(BottomNavItem.Home.route)
}

//-------------------------------------------------------
// MAIN DASHBOARD SCREEN
//-------------------------------------------------------

@Composable
fun DashboardScreen(navController: NavHostController, userName: String? = null) {

    val caItems = remember { monthlyCurrentAffairsItems }
    val testItems = remember { monthlyCurrentAffairsTestItems }

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
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        item { DashboardHeroCard(userName) }

        item {
            SubjectChipRow(
                subjects = listOf("History", "Geography", "Polity", "Economy", "Science", "Environment"),
                onSubjectClick = { subject ->
                    val encoded = java.net.URLEncoder.encode(subject, "UTF-8")
                    navController.navigate("subject_dashboard/$encoded")
                }
            )
        }

        item {
            DashboardSection(
                title = "Monthly Current Affairs",
                caption = "Curated for UPSC CSE",
                pillColor = AccentCyan,
                items = caItems,
                highlightColor = AccentCyan,
                onViewAll = {
                    navController.navigate("dashboard_section/${DashboardSectionType.CurrentAffairs.key}")
                },
                onItemClick = { item -> handleHomeItemNavigation(item, navController) }
            )
        }

        item {
            DashboardSection(
                title = "Monthly Current Affairs Test",
                caption = "Based on your preparation pattern",
                pillColor = AccentLavender,
                items = testItems,
                highlightColor = AccentLavender,
                onViewAll = {
                    navController.navigate("dashboard_section/${DashboardSectionType.CurrentAffairsTest.key}")
                },
                onItemClick = { item -> handleHomeItemNavigation(item, navController) }
            )
        }

        item { Spacer(Modifier.height(8.dp)) }
    }  
}

@Composable
private fun DashboardSection(
    title: String,
    caption: String,
    pillColor: Color,
    items: List<HomeSectionItem>,
    highlightColor: Color,
    onViewAll: () -> Unit,
    onItemClick: (HomeSectionItem) -> Unit = {}
) {
    val visibleItems = items.take(3)

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SectionHeader(
            title = title,
            caption = caption,
            pillColor = pillColor,
            viewButtonLabel = "View all",
            onViewAction = onViewAll
        )

        HorizontalSectionList(
            items = visibleItems,
            highlightColor = highlightColor,
            onItemClick = onItemClick
        )
    }
}

//-------------------------------------------------------
// HERO CARD
//-------------------------------------------------------

@Composable
fun DashboardHeroCard(userName: String?) {
    val displayName = userName?.ifBlank { null } ?: "Aspirant"
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                            AccentPeach.copy(alpha = 0.18f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DashboardBadge(text = "UPSC 2025", color = Gold)
                Text(
                    text = "112 days left",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                )
            }

            Text(
                text = "Welcome back, $displayName 👋",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            )

            Text(
                text = "Dream IAS Elite",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Gold
                )
            )

            Text(
                text = "Let's complete today’s targets and get closer to your rank.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            )

            DailyProgressCard()
        }
    }
}

@Composable
fun DailyProgressCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Daily Test Progress",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Text(
                text = "0/3",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Gold
                )
            )

            Text(
                text = "Keep pace to hit today’s target.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            )

            LinearProgressIndicator(
                progress = 0f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(50)),
                color = AccentCyan,
                trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            )
        }
    }
}

@Composable
fun SubjectChipRow(
    subjects: List<String>,
    onSubjectClick: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(subjects, key = { it }) { subject ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                    .clickable { onSubjectClick(subject) }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text(
                    text = subject,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}

//-------------------------------------------------------
// SECTION HEADER
//-------------------------------------------------------

@Composable
fun SectionHeader(
    title: String,
    caption: String,
    pillColor: Color,
    viewButtonLabel: String?,
    onViewAction: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = caption,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            )
        }

        viewButtonLabel?.let { label ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(pillColor.copy(alpha = 0.20f))
                    .clickable { onViewAction() }
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = label,
                    color = pillColor,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

//-------------------------------------------------------
// HORIZONTAL CAROUSEL (used by BOTH sections)
//-------------------------------------------------------

@Composable
fun HorizontalSectionList(
    items: List<HomeSectionItem>,
    highlightColor: Color,
    onItemClick: (HomeSectionItem) -> Unit = {}
) {

    val cardHeight: Dp = 150.dp

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.padding(top = 6.dp)
    ) {
        items(items) { item ->
            HomeCard(
                item = item,
                highlightColor = highlightColor,
                height = cardHeight,
                onCardClick = { onItemClick(item) },
                modifier = Modifier.width(240.dp)
            )
        }
    }
}

//-------------------------------------------------------
// VIEW ALL SCREEN (VERTICAL LIST)
//-------------------------------------------------------

@Composable
fun DashboardSectionListScreen(
    sectionType: DashboardSectionType,
    navController: NavHostController
) {
    val items = remember { itemsForSection(sectionType) }

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
            SectionHeader(
                title = sectionType.headerTitle,
                caption = sectionType.caption,
                pillColor = sectionType.highlightColor,
                viewButtonLabel = null,
                onViewAction = {}
            )
        }

        items(items, key = { it.title }) { item ->
            HomeCard(
                item = item,
                highlightColor = sectionType.highlightColor,
                modifier = Modifier.fillMaxWidth(),
                onCardClick = { handleHomeItemNavigation(item, navController) }
            )
        }

        item { Spacer(Modifier.height(8.dp)) }
    }
}

//-------------------------------------------------------
// CARD UI (Same for CA + TEST SERIES)
//-------------------------------------------------------

@Composable
fun HomeCard(
    item: HomeSectionItem,
    highlightColor: Color,
    modifier: Modifier = Modifier,
    height: Dp? = null,
    onCardClick: (HomeSectionItem) -> Unit = {}
) {

    Card(
        modifier = modifier
            .then(if (height != null) Modifier.height(height) else Modifier.wrapContentHeight())
            .clickable { onCardClick(item) },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            highlightColor.copy(alpha = 0.16f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DashboardBadge(text = item.tag, color = highlightColor)
                    Text(
                        text = item.meta,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    )
                }

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            }

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 22.dp, y = (-28).dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                highlightColor.copy(alpha = 0.24f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    }
}

@Composable
private fun DashboardBadge(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.14f))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = color
            )
        )
    }
}
