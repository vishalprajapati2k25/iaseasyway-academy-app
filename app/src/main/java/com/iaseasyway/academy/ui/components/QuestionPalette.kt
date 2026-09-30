package com.iaseasyway.academy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iaseasyway.academy.model.ExamAttempt
import com.iaseasyway.academy.model.ExamPaper
import com.iaseasyway.academy.ui.theme.*

@Composable
fun QuestionPalette(
    examPaper: ExamPaper,
    attempt: ExamAttempt,
    currentQuestionIndex: Int,
    onSelectQuestion: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Navy900,
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "Question Palette (${examPaper.questions.size} Questions)",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = PureWhite
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Palette Legend
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LegendIndicator(color = ExamAnsweredGreen, label = "Answered")
                LegendIndicator(color = ExamUnansweredRed, label = "Unanswered")
                LegendIndicator(color = ExamReviewPurple, label = "Review")
                LegendIndicator(color = ExamNotVisitedGrey, label = "Not Visited")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Grid of Question Badges
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 44.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 220.dp)
            ) {
                items(examPaper.questions.size) { idx ->
                    val qNum = examPaper.questions[idx].number
                    val isAnswered = attempt.answers.containsKey(qNum)
                    val isReview = attempt.reviewMarked.contains(qNum)
                    val isCurrent = currentQuestionIndex == idx

                    val bgColor = when {
                        isReview -> ExamReviewPurple
                        isAnswered -> ExamAnsweredGreen
                        idx <= currentQuestionIndex -> ExamUnansweredRed
                        else -> ExamNotVisitedGrey
                    }

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(bgColor)
                            .then(
                                if (isCurrent) Modifier.border(2.dp, DuolingoGold, CircleShape) else Modifier
                            )
                            .clickable { onSelectQuestion(idx) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$qNum",
                            color = PureWhite,
                            fontSize = 13.sp,
                            fontWeight = if (isCurrent) FontWeight.Black else FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LegendIndicator(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = PureWhite
        )
    }
}
