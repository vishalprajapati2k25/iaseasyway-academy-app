package com.iaseasyway.academy.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.window.Dialog
import com.iaseasyway.academy.data.repository.AcademyRepository
import com.iaseasyway.academy.model.ExamAttempt
import com.iaseasyway.academy.model.ExamPaper
import com.iaseasyway.academy.ui.components.QuestionPalette
import com.iaseasyway.academy.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun ExamSimulationScreen(
    repository: AcademyRepository,
    onExamFinished: () -> Unit,
    onExitExam: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activePaper by repository.activeExamPaper.collectAsState()
    val activeAttempt by repository.activeExamAttempt.collectAsState()

    val paper = activePaper ?: return
    val attempt = activeAttempt ?: ExamAttempt(paperId = paper.id)

    var currentQuestionIdx by remember { mutableStateOf(0) }
    var showPaletteSheet by remember { mutableStateOf(false) }
    var showSubmitConfirmation by remember { mutableStateOf(false) }
    var isMarathiLanguage by remember { mutableStateOf(false) }

    // Real-Time Countdown Timer (e.g. 120 minutes)
    var secondsRemaining by remember { mutableStateOf(paper.durationMinutes * 60) }

    LaunchedEffect(Unit) {
        while (secondsRemaining > 0) {
            delay(1000)
            secondsRemaining--
        }
        if (secondsRemaining <= 0) {
            repository.submitExam()
            onExamFinished()
        }
    }

    val currentQuestion = paper.questions.getOrNull(currentQuestionIdx) ?: paper.questions.first()
    val selectedOption = attempt.answers[currentQuestion.number]
    val isMarkedForReview = attempt.reviewMarked.contains(currentQuestion.number)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Slate100)
    ) {
        // Exam Navigation Top Bar
        Surface(
            color = Navy900,
            elevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = paper.title,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite,
                            maxLines = 1
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(DuolingoRed)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "DRM & Anti-Screenshot Active",
                                fontSize = 10.sp,
                                color = DuolingoGold
                            )
                        }
                    }

                    // Countdown Timer Pill
                    val hours = secondsRemaining / 3600
                    val minutes = (secondsRemaining % 3600) / 60
                    val seconds = secondsRemaining % 60
                    val timeStr = if (hours > 0) {
                        String.format("%02d:%02d:%02d", hours, minutes, seconds)
                    } else {
                        String.format("%02d:%02d", minutes, seconds)
                    }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Navy800)
                            .border(1.dp, if (secondsRemaining < 300) DuolingoRed else DuolingoGreen, RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = "Timer",
                            tint = if (secondsRemaining < 300) DuolingoRed else DuolingoGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = timeStr,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            color = PureWhite
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Final Submit Button
                    Button(
                        onClick = { showSubmitConfirmation = true },
                        colors = ButtonDefaults.buttonColors(backgroundColor = DuolingoGreen),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text(
                            text = "SUBMIT",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Question Navigation Ribbon & Language Switcher
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Question ${currentQuestion.number} of ${paper.questions.size}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DuolingoGold
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Bilingual Toggle Button
                        if (currentQuestion.textMarathi != null) {
                            TextButton(
                                onClick = { isMarathiLanguage = !isMarathiLanguage },
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (isMarathiLanguage) "English" else "मराठी",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DuolingoGold
                                )
                            }
                        }

                        // Open Palette Button
                        TextButton(
                            onClick = { showPaletteSheet = !showPaletteSheet },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.GridView,
                                contentDescription = "Palette",
                                tint = PureWhite,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Palette",
                                fontSize = 11.sp,
                                color = PureWhite
                            )
                        }
                    }
                }
            }
        }

        // Palette Collapsible View
        AnimatedVisibility(visible = showPaletteSheet) {
            QuestionPalette(
                examPaper = paper,
                attempt = attempt,
                currentQuestionIndex = currentQuestionIdx,
                onSelectQuestion = {
                    currentQuestionIdx = it
                    showPaletteSheet = false
                }
            )
        }

        // Question Details & Options Area
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Subject & Previous Year Metadata Tag
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Navy700)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = currentQuestion.subject,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(DuolingoGold.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = currentQuestion.pyqYear,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = DuolingoGoldDark
                        )
                    }
                }
            }

            // Question Statement
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor = PureWhite,
                    elevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val displayText = if (isMarathiLanguage && currentQuestion.textMarathi != null) {
                        currentQuestion.textMarathi
                    } else {
                        currentQuestion.text
                    }

                    Text(
                        text = displayText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Options
            val optionsList = if (isMarathiLanguage && currentQuestion.optionsMarathi != null) {
                currentQuestion.optionsMarathi
            } else {
                currentQuestion.options
            }

            items(optionsList.size) { optIdx ->
                val isSelected = selectedOption == optIdx
                Card(
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = if (isSelected) Navy700.copy(alpha = 0.1f) else PureWhite,
                    elevation = if (isSelected) 3.dp else 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) Navy700 else Slate300,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            repository.selectExamAnswer(currentQuestion.number, optIdx)
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { repository.selectExamAnswer(currentQuestion.number, optIdx) },
                            colors = RadioButtonDefaults.colors(selectedColor = Navy700)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = optionsList[optIdx],
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = TextDark
                        )
                    }
                }
            }
        }

        // Bottom Action Command Center (Exam Controller)
        Surface(
            color = PureWhite,
            elevation = 12.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Clear Response
                OutlinedButton(
                    onClick = { repository.clearExamAnswer(currentQuestion.number) },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(42.dp)
                ) {
                    Text(
                        text = "Clear",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextMuted
                    )
                }

                // Mark for Review
                Button(
                    onClick = {
                        repository.toggleExamReview(currentQuestion.number)
                        if (currentQuestionIdx + 1 < paper.questions.size) {
                            currentQuestionIdx++
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isMarkedForReview) ExamReviewPurple else Navy800
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(42.dp)
                ) {
                    Text(
                        text = if (isMarkedForReview) "Review ✓" else "Review",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }

                // Save & Next
                Button(
                    onClick = {
                        if (currentQuestionIdx + 1 < paper.questions.size) {
                            currentQuestionIdx++
                        } else {
                            showSubmitConfirmation = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = DuolingoGreen),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(42.dp)
                ) {
                    Text(
                        text = if (currentQuestionIdx + 1 < paper.questions.size) "Save & Next" else "Submit",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }
            }
        }

        // Submit Confirmation Modal
        if (showSubmitConfirmation) {
            val answeredCount = attempt.answers.size
            val reviewCount = attempt.reviewMarked.size
            val unattemptedCount = paper.questions.size - answeredCount

            Dialog(onDismissRequest = { showSubmitConfirmation = false }) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = Navy900,
                    elevation = 16.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Submit Examination?",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Please verify your question response breakdown:",
                            fontSize = 12.sp,
                            color = TextMuted
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Navy800)
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "$answeredCount", color = ExamAnsweredGreen, fontWeight = FontWeight.Black, fontSize = 18.sp)
                                Text(text = "Answered", color = PureWhite, fontSize = 10.sp)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "$reviewCount", color = ExamReviewPurple, fontWeight = FontWeight.Black, fontSize = 18.sp)
                                Text(text = "Review", color = PureWhite, fontSize = 10.sp)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "$unattemptedCount", color = ExamUnansweredRed, fontWeight = FontWeight.Black, fontSize = 18.sp)
                                Text(text = "Remaining", color = PureWhite, fontSize = 10.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = { showSubmitConfirmation = false },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Resume Test", color = PureWhite, fontSize = 12.sp)
                            }

                            Button(
                                onClick = {
                                    showSubmitConfirmation = false
                                    repository.submitExam()
                                    onExamFinished()
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = DuolingoGreen),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Confirm Submit", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
