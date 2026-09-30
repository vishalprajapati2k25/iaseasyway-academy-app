package com.iaseasyway.academy.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.iaseasyway.academy.model.*
import com.iaseasyway.academy.ui.theme.*

enum class CurrentAffairsSubTab(val label: String) {
    EDITORIALS("Daily Editorials & Probable Qs"),
    MEMORY_HACKS("Reverse Psychology Memory Hacks")
}

@Composable
fun CurrentAffairsHubScreen(
    repository: AcademyRepository,
    onStartExamFromTopic: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var activeSubTab by remember { mutableStateOf(CurrentAffairsSubTab.EDITORIALS) }
    val editorials by repository.editorials.collectAsState()
    val memoryShortcuts by repository.memoryShortcuts.collectAsState()

    var selectedNewspaper by remember { mutableStateOf(NewspaperSource.ALL) }
    var selectedSubjectFilter by remember { mutableStateOf("ALL") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Slate100)
    ) {
        // Hub Top Bar
        Surface(
            color = Navy900,
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Current Affairs & Editorial Intelligence",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
                Text(
                    text = "The Hindu • Loksatta • Indian Express + Reverse Psychology Memory Shortcuts",
                    fontSize = 12.sp,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Segmented Tab Switcher
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Navy800)
                        .padding(4.dp)
                ) {
                    CurrentAffairsSubTab.values().forEach { tab ->
                        val isSelected = activeSubTab == tab
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) DuolingoGreen else Color.Transparent)
                                .clickable { activeSubTab = tab }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = tab.label,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) PureWhite else TextMuted
                            )
                        }
                    }
                }
            }
        }

        // Sub-Tab Content
        when (activeSubTab) {
            CurrentAffairsSubTab.EDITORIALS -> {
                EditorialsSection(
                    editorials = editorials,
                    selectedNewspaper = selectedNewspaper,
                    onSelectNewspaper = { selectedNewspaper = it }
                )
            }
            CurrentAffairsSubTab.MEMORY_HACKS -> {
                MemoryHacksSection(
                    shortcuts = memoryShortcuts,
                    selectedSubject = selectedSubjectFilter,
                    onSelectSubject = { selectedSubjectFilter = it }
                )
            }
        }
    }
}

@Composable
private fun EditorialsSection(
    editorials: List<EditorialArticle>,
    selectedNewspaper: NewspaperSource,
    onSelectNewspaper: (NewspaperSource) -> Unit
) {
    val filtered = editorials.filter {
        selectedNewspaper == NewspaperSource.ALL || it.newspaper == selectedNewspaper
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Newspaper Filter Pills
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(NewspaperSource.values()) { src ->
                    val isSelected = selectedNewspaper == src
                    val pillColor = if (isSelected) DuolingoGreen else PureWhite
                    val textColor = if (isSelected) PureWhite else TextDark

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(pillColor)
                            .border(1.dp, if (isSelected) DuolingoGreenDark else Slate300, RoundedCornerShape(20.dp))
                            .clickable { onSelectNewspaper(src) }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = src.displayName,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = textColor
                        )
                    }
                }
            }
        }

        items(filtered) { article ->
            EditorialArticleCard(article = article)
        }
    }
}

@Composable
private fun EditorialArticleCard(article: EditorialArticle) {
    var showPrelimsQuiz by remember { mutableStateOf(false) }
    var showMainsFramework by remember { mutableStateOf(false) }
    var selectedPrelimsOption by remember { mutableStateOf<Int?>(null) }

    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = PureWhite,
        elevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Source & Date Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(article.newspaper.badgeColorHex))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = article.newspaper.displayName,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }
                Text(
                    text = article.date,
                    fontSize = 11.sp,
                    color = TextMuted,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Headline
            Text(
                text = article.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextDark,
                lineHeight = 22.sp
            )

            Text(
                text = article.subtitle,
                fontSize = 12.sp,
                color = TextMuted,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 3-Minute Summary
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Slate100)
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = "⏱️ 3-Minute Editorial Summary:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy700
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = article.summary3Min,
                        fontSize = 12.sp,
                        color = TextDark,
                        lineHeight = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Syllabus & Topic Linkage Pills
            Text(
                text = "Linked Syllabus & Static Topics:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                article.syllabusTopics.take(2).forEach { topic ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Navy800.copy(alpha = 0.08f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = topic.take(30) + "...",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Navy900
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Probable Prelims Question Toggle Button
            OutlinedButton(
                onClick = { showPrelimsQuiz = !showPrelimsQuiz },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (showPrelimsQuiz) Icons.Default.ExpandLess else Icons.Default.Quiz,
                    contentDescription = null,
                    tint = Navy700,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (showPrelimsQuiz) "Hide Probable Prelims MCQ" else "⚡ Solve Probable Prelims Question on this Editorial",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy700
                )
            }

            // Expandable Prelims Quiz
            AnimatedVisibility(visible = showPrelimsQuiz) {
                val q = article.probablePrelimsQuestion
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Slate100)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Probable Prelims MCQ:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DuolingoGoldDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = q.text,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    q.options.forEachIndexed { idx, opt ->
                        val isSelected = selectedPrelimsOption == idx
                        val isCorrect = q.correctOptionIndex == idx

                        val optColor = when {
                            selectedPrelimsOption != null && isCorrect -> DuolingoGreen.copy(alpha = 0.2f)
                            selectedPrelimsOption != null && isSelected && !isCorrect -> DuolingoRed.copy(alpha = 0.2f)
                            isSelected -> Navy700.copy(alpha = 0.1f)
                            else -> PureWhite
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(optColor)
                                .border(1.dp, if (isSelected) Navy700 else Slate300, RoundedCornerShape(8.dp))
                            .clickable { selectedPrelimsOption = idx }
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = opt,
                                fontSize = 12.sp,
                                color = TextDark,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }

                    if (selectedPrelimsOption != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Explanation: ${q.explanation}",
                            fontSize = 11.sp,
                            color = TextDark,
                            lineHeight = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Probable Mains Question Toggle Button
            OutlinedButton(
                onClick = { showMainsFramework = !showMainsFramework },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (showMainsFramework) Icons.Default.ExpandLess else Icons.Default.EditNote,
                    contentDescription = null,
                    tint = DuolingoGreenDark,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (showMainsFramework) "Hide Mains Model Framework" else "✍️ Probable Mains Question & Framework",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = DuolingoGreenDark
                )
            }

            // Expandable Mains Question & Framework
            AnimatedVisibility(visible = showMainsFramework) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(DuolingoGreen.copy(alpha = 0.08f))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Probable Mains Analytical Question:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DuolingoGreenDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = article.probableMainsQuestion,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Model Answer Structure & Dimensions:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy900
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    article.mainsModelFramework.forEach { step ->
                        Text(
                            text = "• $step",
                            fontSize = 11.sp,
                            color = TextDark,
                            lineHeight = 15.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }

            // Embedded Memory Shortcut if available
            article.memoryShortcut?.let { sc ->
                Spacer(modifier = Modifier.height(10.dp))
                MemoryShortcutBanner(shortcut = sc)
            }
        }
    }
}

@Composable
private fun MemoryHacksSection(
    shortcuts: List<MemoryShortcut>,
    selectedSubject: String,
    onSelectSubject: (String) -> Unit
) {
    val subjects = listOf("ALL", "Polity", "Geography", "Maharashtra", "Economy", "Speed Math")
    val filtered = shortcuts.filter {
        selectedSubject == "ALL" || it.subject.contains(selectedSubject, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Banner with Reverse Psychology Explanation
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = DuolingoGold,
                elevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Navy900),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🧠", fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Reverse Psychology Memory Anchors",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = Navy900
                        )
                        Text(
                            text = "Pre-topic curiosity teasers + post-topic mnemonics that make concepts impossible to forget.",
                            fontSize = 11.sp,
                            color = Navy900.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }

        // Subject Filters
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(subjects) { subj ->
                    val isSelected = selectedSubject == subj
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) Navy900 else PureWhite)
                            .border(1.dp, if (isSelected) Navy900 else Slate300, RoundedCornerShape(20.dp))
                            .clickable { onSelectSubject(subj) }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = subj,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) PureWhite else TextDark
                        )
                    }
                }
            }
        }

        items(filtered) { sc ->
            MemoryShortcutFullCard(shortcut = sc)
        }
    }
}

@Composable
private fun MemoryShortcutFullCard(shortcut: MemoryShortcut) {
    var isMnemonicRevealed by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = PureWhite,
        elevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Subject & Topic Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Navy700)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = shortcut.subject,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }
                Text(
                    text = shortcut.whenToUse,
                    fontSize = 10.sp,
                    color = TextMuted,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = shortcut.topic,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Reverse Psychology Teaser Hook (Amber Warning Style)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(DuolingoGold.copy(alpha = 0.15f))
                    .border(1.dp, DuolingoGold, RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(text = "⚠️", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Reverse Psychology Pre-Learning Hook:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = DuolingoGoldDark
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = shortcut.reversePsychologyHook,
                            fontSize = 12.sp,
                            color = TextDark,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Reveal Mnemonic Button / Banner
            if (!isMnemonicRevealed) {
                Button(
                    onClick = { isMnemonicRevealed = true },
                    colors = ButtonDefaults.buttonColors(backgroundColor = DuolingoGreen),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(40.dp)
                ) {
                    Icon(imageVector = Icons.Default.Visibility, contentDescription = null, tint = PureWhite, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "REVEAL MEMORY SHORTCUT & MNEMONIC",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Navy900)
                        .padding(14.dp)
                ) {
                    Text(
                        text = "MNEMONIC CODE:",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = DuolingoGold
                    )
                    Text(
                        text = shortcut.mnemonicCode,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = PureWhite,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Divider(color = Navy800, modifier = Modifier.padding(vertical = 6.dp))

                    Text(
                        text = "Letter-by-Letter Breakdown:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = DuolingoGreen
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    shortcut.breakdown.forEach { item ->
                        Text(
                            text = "• $item",
                            fontSize = 11.sp,
                            color = PureWhite,
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = shortcut.fullExplanation,
                        fontSize = 11.sp,
                        color = TextMuted,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MemoryShortcutBanner(shortcut: MemoryShortcut) {
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Navy900,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "💡", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Topic Memory Shortcut: ${shortcut.topic}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DuolingoGold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = shortcut.mnemonicCode,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = PureWhite
            )
            Text(
                text = shortcut.fullExplanation,
                fontSize = 10.sp,
                color = TextMuted,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}
