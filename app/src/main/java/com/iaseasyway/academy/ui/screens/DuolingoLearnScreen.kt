package com.iaseasyway.academy.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.iaseasyway.academy.data.repository.AcademyRepository
import com.iaseasyway.academy.model.*
import com.iaseasyway.academy.ui.theme.*

@Composable
fun DuolingoLearnScreen(
    repository: AcademyRepository,
    modifier: Modifier = Modifier
) {
    val units by repository.gamifiedUnits.collectAsState()
    val userProfile by repository.userProfile.collectAsState()

    var activeStageForPlay by remember { mutableStateOf<Pair<GamifiedLessonUnit, GamifiedStage>?>(null) }

    Box(modifier = modifier.fillMaxSize().background(Slate100)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Gamification Motivation Banner
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = DuolingoGreen,
                    elevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(PureWhite),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🦉", fontSize = 28.sp)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Play to Learn! (Duolingo Style)",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                color = PureWhite
                            )
                            Text(
                                text = "Bite-sized civil service lessons. Keep your ${userProfile.streakDays}-day streak burning!",
                                fontSize = 12.sp,
                                color = PureWhite.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }

            // Units Loop
            items(units) { unit ->
                UnitPathSection(
                    unit = unit,
                    onStageClick = { stage ->
                        if (stage.isUnlocked) {
                            activeStageForPlay = Pair(unit, stage)
                        }
                    }
                )
            }
        }

        // Active Gamified Play Dialog
        activeStageForPlay?.let { (unit, stage) ->
            GamifiedPlayModal(
                unit = unit,
                stage = stage,
                repository = repository,
                onDismiss = { activeStageForPlay = null }
            )
        }
    }
}

@Composable
private fun UnitPathSection(
    unit: GamifiedLessonUnit,
    onStageClick: (GamifiedStage) -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = PureWhite,
        elevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Unit Header Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.horizontalGradient(listOf(Navy800, Navy700))
                    )
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Column {
                    Text(
                        text = "UNIT ${unit.unitNumber}: ${unit.title.uppercase()}",
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        color = DuolingoGold
                    )
                    Text(
                        text = unit.topic,
                        fontSize = 12.sp,
                        color = PureWhite
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Stepping Stones (Nodes in a playful zigzag layout)
            unit.stages.forEachIndexed { index, stage ->
                val xOffset = when (index % 3) {
                    0 -> 0.dp
                    1 -> 36.dp
                    else -> (-36).dp
                }

                Box(
                    modifier = Modifier
                        .offset(x = xOffset)
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    StageNodeButton(
                        stage = stage,
                        onClick = { onStageClick(stage) }
                    )
                }
            }
        }
    }
}

@Composable
private fun StageNodeButton(
    stage: GamifiedStage,
    onClick: () -> Unit
) {
    val bgColor = when {
        stage.isCompleted -> DuolingoGreen
        stage.isUnlocked -> DuolingoGold
        else -> Slate300
    }

    val shadowColor = when {
        stage.isCompleted -> DuolingoGreenDark
        stage.isUnlocked -> DuolingoGoldDark
        else -> Slate700.copy(alpha = 0.3f)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(enabled = stage.isUnlocked) { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)
                .background(bgColor)
                .border(4.dp, shadowColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (stage.isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completed",
                    tint = PureWhite,
                    modifier = Modifier.size(34.dp)
                )
            } else if (stage.isUnlocked) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Navy900,
                    modifier = Modifier.size(36.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = Slate700,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stage.title,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (stage.isUnlocked) TextDark else TextMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.widthIn(max = 120.dp)
        )

        if (stage.isCompleted) {
            Row {
                repeat(3) { starIdx ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = if (starIdx < stage.stars) DuolingoGold else Slate300,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GamifiedPlayModal(
    unit: GamifiedLessonUnit,
    stage: GamifiedStage,
    repository: AcademyRepository,
    onDismiss: () -> Unit
) {
    var currentQuestionIdx by remember { mutableStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }
    var isVictory by remember { mutableStateOf(false) }
    var correctCount by remember { mutableStateOf(0) }

    val questions = stage.questions
    val question = questions.getOrNull(currentQuestionIdx)

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            backgroundColor = PureWhite,
            elevation = 16.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Top Progress & Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Exit")
                    }

                    if (!isVictory && questions.isNotEmpty()) {
                        val progress = (currentQuestionIdx + 1).toFloat() / questions.size
                        LinearProgressIndicator(
                            progress = progress,
                            modifier = Modifier
                                .weight(1f)
                                .height(10.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            color = DuolingoGreen,
                            backgroundColor = Slate200
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "⚡ +${stage.xpReward} XP",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = DuolingoGold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isVictory || questions.isEmpty()) {
                    // Stage Victory View
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🎉", fontSize = 48.sp)
                        Text(
                            text = "Lesson Complete!",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = DuolingoGreen
                        )
                        Text(
                            text = "You mastered ${stage.title} and earned +${stage.xpReward} XP!",
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color = TextDark,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            repeat(3) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Star",
                                    tint = DuolingoGold,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                repository.completeGamifiedStage(unit.id, stage.id, stars = 3, xpBonus = stage.xpReward)
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = DuolingoGreen),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Text(
                                text = "CONTINUE",
                                fontWeight = FontWeight.Black,
                                fontSize = 15.sp,
                                color = PureWhite
                            )
                        }
                    }
                } else if (question != null) {
                    // Question Prompt
                    Text(
                        text = question.prompt,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )

                    // Pre-Topic Reverse Psychology Teaser Hook (fits better in memory before learning)
                    question.mnemonic?.let { mnemonicText ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(DuolingoGold.copy(alpha = 0.12f))
                                .border(1.dp, DuolingoGold, RoundedCornerShape(8.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🧠", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Pre-Learning Hook: Don't guess randomly! Notice the keyword pattern before you pick your option.",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = DuolingoGoldDark
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Options
                    question.options.forEachIndexed { idx, optText ->
                        val isSelected = selectedOption == idx
                        val isCorrect = question.correctOptionIndex == idx

                        val optBg = when {
                            isSubmitted && isCorrect -> DuolingoGreen.copy(alpha = 0.2f)
                            isSubmitted && isSelected && !isCorrect -> DuolingoRed.copy(alpha = 0.2f)
                            isSelected -> Navy700.copy(alpha = 0.1f)
                            else -> Slate100
                        }

                        val borderColor = when {
                            isSubmitted && isCorrect -> DuolingoGreen
                            isSubmitted && isSelected && !isCorrect -> DuolingoRed
                            isSelected -> Navy700
                            else -> Slate300
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(optBg)
                                .border(2.dp, borderColor, RoundedCornerShape(12.dp))
                                .clickable(enabled = !isSubmitted) { selectedOption = idx }
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(borderColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = ('A' + idx).toString(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = PureWhite
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = optText,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = TextDark
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Feedback Banner
                    if (isSubmitted) {
                        val wasCorrect = selectedOption == question.correctOptionIndex
                        Card(
                            backgroundColor = if (wasCorrect) DuolingoGreen.copy(alpha = 0.15f) else DuolingoRed.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = if (wasCorrect) "🎉 Excellent! That is correct!" else "❌ Not quite right",
                                    fontWeight = FontWeight.Bold,
                                    color = if (wasCorrect) DuolingoGreenDark else DuolingoRedDark,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = question.explanation,
                                    fontSize = 12.sp,
                                    color = TextDark,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                question.mnemonic?.let {
                                    Text(
                                        text = it,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = DuolingoGoldDark,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Bottom Action Button
                    Button(
                        onClick = {
                            if (!isSubmitted) {
                                if (selectedOption != null) {
                                    isSubmitted = true
                                    if (selectedOption == question.correctOptionIndex) {
                                        correctCount++
                                    } else {
                                        repository.deductHeart()
                                    }
                                }
                            } else {
                                // Next Question or Victory
                                if (currentQuestionIdx + 1 < questions.size) {
                                    currentQuestionIdx++
                                    selectedOption = null
                                    isSubmitted = false
                                } else {
                                    isVictory = true
                                }
                            }
                        },
                        enabled = selectedOption != null,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (isSubmitted) DuolingoGreen else Navy700
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = if (!isSubmitted) "CHECK" else if (currentQuestionIdx + 1 < questions.size) "NEXT QUESTION" else "FINISH",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = PureWhite
                        )
                    }
                }
            }
        }
    }
}
