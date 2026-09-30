package com.iaseasyway.academy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iaseasyway.academy.data.repository.AcademyRepository
import com.iaseasyway.academy.data.seed.SeedData
import com.iaseasyway.academy.model.SchoolGradeItem
import com.iaseasyway.academy.ui.theme.*

@Composable
fun SchoolClassHubScreen(
    repository: AcademyRepository,
    onGradeSelected: (String) -> Unit,
    onStartExamFromGrade: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val userProfile by repository.userProfile.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Slate100)
    ) {
        // Header
        Surface(
            color = Navy900,
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "School & Junior Talent Foundation Hub",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
                Text(
                    text = "Select any Class from 4th to Degree: 4th/5th Scholarship, 5th Navodaya, Olympiads & Civil Services",
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }
        }

        // Grades List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(SeedData.schoolGrades) { gradeItem ->
                GradeCard(
                    gradeItem = gradeItem,
                    isActive = userProfile.currentGrade.contains(gradeItem.gradeName),
                    onSelectGrade = {
                        repository.updateSelectedGrade(gradeItem.displayName)
                        onGradeSelected(gradeItem.displayName)
                    },
                    onPracticeClick = {
                        repository.updateSelectedGrade(gradeItem.displayName)
                        onStartExamFromGrade(gradeItem.gradeName)
                    }
                )
            }
        }
    }
}

@Composable
private fun GradeCard(
    gradeItem: SchoolGradeItem,
    isActive: Boolean,
    onSelectGrade: () -> Unit,
    onPracticeClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = PureWhite,
        elevation = if (isActive) 5.dp else 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectGrade() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isActive) DuolingoGreen else Navy700)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = gradeItem.gradeName,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            color = PureWhite
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = gradeItem.displayName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                }

                if (isActive) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(DuolingoGreen.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "ACTIVE TRACK",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = DuolingoGreenDark
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Key Foundation Subjects:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                gradeItem.keySubjects.take(3).forEach { subj ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Slate100)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = subj,
                            fontSize = 11.sp,
                            color = TextDark,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = gradeItem.foundationFocus,
                fontSize = 12.sp,
                color = TextDark,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onSelectGrade,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f).height(40.dp)
                ) {
                    Text(
                        text = if (isActive) "SELECTED ✓" else "SET AS TARGET",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isActive) DuolingoGreenDark else Navy700
                    )
                }

                Button(
                    onClick = onPracticeClick,
                    colors = ButtonDefaults.buttonColors(backgroundColor = DuolingoGreen),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f).height(40.dp)
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = PureWhite, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "PRACTICE EXAM",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }
            }
        }
    }
}
