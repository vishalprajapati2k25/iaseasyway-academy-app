package com.iaseasyway.academy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iaseasyway.academy.data.repository.AcademyRepository
import com.iaseasyway.academy.model.QuestionReview
import com.iaseasyway.academy.ui.theme.*

@Composable
fun ExamResultScreen(
    repository: AcademyRepository,
    onBackToHub: () -> Unit,
    modifier: Modifier = Modifier
) {
    val result by repository.examScoreResult.collectAsState()
    val res = result ?: return

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Slate100)
    ) {
        // Result Scorecard Header
        Surface(
            color = Navy900,
            elevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Examination Performance Report",
                    fontSize = 13.sp,
                    color = DuolingoGold,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = res.paperTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Score Circle & Percentile
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${res.rawScore}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = DuolingoGreen
                        )
                        Text(text = "Final Score", fontSize = 11.sp, color = TextMuted)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${res.accuracyPercentage}%",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = DuolingoBlue
                        )
                        Text(text = "Accuracy", fontSize = 11.sp, color = TextMuted)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${res.percentileEstimate}th",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            color = DuolingoGold
                        )
                        Text(text = "Est. Percentile", fontSize = 11.sp, color = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Breakdown Pills (Correct / Incorrect / Skipped)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Navy800)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    BreakdownItem(color = ExamAnsweredGreen, label = "Correct: ${res.correctCount}")
                    BreakdownItem(color = ExamUnansweredRed, label = "Incorrect: ${res.incorrectCount}")
                    BreakdownItem(color = ExamNotVisitedGrey, label = "Unattempted: ${res.unattemptedCount}")
                }
            }
        }

        // Question-by-Question Review with Explanations
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    text = "Detailed Solutions & Pedagogical Explanations",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextDark
                )
            }

            items(res.reviews) { review ->
                SolutionReviewCard(review = review)
            }
        }

        // Return Home Action
        Surface(
            color = PureWhite,
            elevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = onBackToHub,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Navy700),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "RETURN TO EXAMS HUB",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = PureWhite
                    )
                }
            }
        }
    }
}

@Composable
private fun SolutionReviewCard(review: QuestionReview) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = PureWhite,
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Q${review.questionNumber}",
                    fontWeight = FontWeight.Black,
                    fontSize = 14.sp,
                    color = Navy900
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (review.isCorrect) ExamAnsweredGreen.copy(alpha = 0.15f) else ExamUnansweredRed.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (review.isCorrect) "✓ Correct (+Marks)" else if (review.selectedOption != null) "✗ Incorrect (-Penalty)" else "— Skipped",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (review.isCorrect) ExamAnsweredGreen else if (review.selectedOption != null) ExamUnansweredRed else TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = review.questionText,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = TextDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Explanation Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Slate100)
                    .border(1.dp, Slate200, RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Column {
                    Text(
                        text = "Correct Answer: Option ${('A' + review.correctOption)}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = DuolingoGreenDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = review.explanation,
                        fontSize = 12.sp,
                        color = TextDark,
                        lineHeight = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun BreakdownItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, color = PureWhite, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}
