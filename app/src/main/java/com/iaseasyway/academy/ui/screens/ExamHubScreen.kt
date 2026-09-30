package com.iaseasyway.academy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iaseasyway.academy.data.repository.AcademyRepository
import com.iaseasyway.academy.model.ExamCategory
import com.iaseasyway.academy.model.ExamPaper
import com.iaseasyway.academy.ui.theme.*

@Composable
fun ExamHubScreen(
    repository: AcademyRepository,
    onStartExam: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val examPapers by repository.examPapers.collectAsState()
    var selectedCategory by remember { mutableStateOf(ExamCategory.ALL) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredPapers = examPapers.filter { paper ->
        val matchesCategory = selectedCategory == ExamCategory.ALL || paper.examCategory == selectedCategory
        val matchesSearch = searchQuery.isBlank() ||
                paper.title.contains(searchQuery, ignoreCase = true) ||
                paper.targetClass.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Slate100)
    ) {
        // Search & Filter Header
        Card(
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
            backgroundColor = Navy900,
            elevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Time-Based Exam Simulation & PYQs",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PureWhite
                )
                Text(
                    text = "Previous Year Papers (All Years to Date) constructed for upcoming exams",
                    fontSize = 12.sp,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search UPSC, MPSC, Scholarship, Navodaya, Olympiads...", color = TextMuted) },
                    leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = TextMuted) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Navy800),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = PureWhite,
                        cursorColor = DuolingoGold,
                        focusedBorderColor = DuolingoGreen,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Category Filter Pills
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(ExamCategory.values()) { cat ->
                        val isSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) DuolingoGreen else Navy800)
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = cat.displayName,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) PureWhite else TextMuted
                            )
                        }
                    }
                }
            }
        }

        // Exam Paper Cards List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Available Test Sets (${filteredPapers.size})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = TextDark
                    )
                    Text(
                        text = "Real Exam Interface",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Navy600
                    )
                }
            }

            items(filteredPapers) { paper ->
                ExamPaperCard(
                    paper = paper,
                    onStartClick = { onStartExam(paper.id) }
                )
            }
        }
    }
}

@Composable
private fun ExamPaperCard(
    paper: ExamPaper,
    onStartClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = PureWhite,
        elevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Navy700)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = paper.examCategory.shortBadge,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = PureWhite
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(DuolingoGold.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "${paper.year} Pattern",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = DuolingoGoldDark
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = paper.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = TextDark
                    )

                    Text(
                        text = "Target: ${paper.targetClass}",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Test Metadata Badges (Questions, Duration, Negative Marks)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Slate100)
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ExamMetaItem(icon = Icons.Default.HelpOutline, label = "${paper.questions.size} Qs")
                ExamMetaItem(icon = Icons.Default.Timer, label = "${paper.durationMinutes} Mins")
                ExamMetaItem(icon = Icons.Default.Cancel, label = "-${paper.negativeMarking} Neg")
                ExamMetaItem(icon = Icons.Default.MilitaryTech, label = "${paper.totalMarks} Marks")
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onStartClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = DuolingoGreen),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayCircle,
                    contentDescription = null,
                    tint = PureWhite,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "START TIMED EXAM",
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp,
                    color = PureWhite
                )
            }
        }
    }
}

@Composable
private fun ExamMetaItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Navy700,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark
        )
    }
}
