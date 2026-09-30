package com.iaseasyway.academy.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iaseasyway.academy.ui.theme.DuolingoGreen
import com.iaseasyway.academy.ui.theme.Navy900
import com.iaseasyway.academy.ui.theme.PureWhite
import com.iaseasyway.academy.ui.theme.TextMuted

enum class AcademyTab(val title: String) {
    LEARN("Play"),
    EDITORIALS("Editorials & Hacks"),
    EXAMS("Exams"),
    COURSES("Courses"),
    SCHOOL("School Hub")
}

@Composable
fun AcademyBottomBar(
    currentTab: AcademyTab,
    onTabSelected: (AcademyTab) -> Unit
) {
    BottomNavigation(
        backgroundColor = Navy900,
        contentColor = PureWhite,
        elevation = 8.dp
    ) {
        BottomNavigationItem(
            selected = currentTab == AcademyTab.LEARN,
            onClick = { onTabSelected(AcademyTab.LEARN) },
            icon = {
                Icon(
                    imageVector = Icons.Default.SportsEsports,
                    contentDescription = "Play to Learn"
                )
            },
            label = { Text(AcademyTab.LEARN.title) },
            selectedContentColor = DuolingoGreen,
            unselectedContentColor = TextMuted
        )

        BottomNavigationItem(
            selected = currentTab == AcademyTab.EDITORIALS,
            onClick = { onTabSelected(AcademyTab.EDITORIALS) },
            icon = {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = "Editorials & Hacks"
                )
            },
            label = { Text(AcademyTab.EDITORIALS.title) },
            selectedContentColor = DuolingoGreen,
            unselectedContentColor = TextMuted
        )

        BottomNavigationItem(
            selected = currentTab == AcademyTab.EXAMS,
            onClick = { onTabSelected(AcademyTab.EXAMS) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = "Practice Exams"
                )
            },
            label = { Text(AcademyTab.EXAMS.title) },
            selectedContentColor = DuolingoGreen,
            unselectedContentColor = TextMuted
        )

        BottomNavigationItem(
            selected = currentTab == AcademyTab.COURSES,
            onClick = { onTabSelected(AcademyTab.COURSES) },
            icon = {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = "Academy Courses"
                )
            },
            label = { Text(AcademyTab.COURSES.title) },
            selectedContentColor = DuolingoGreen,
            unselectedContentColor = TextMuted
        )

        BottomNavigationItem(
            selected = currentTab == AcademyTab.SCHOOL,
            onClick = { onTabSelected(AcademyTab.SCHOOL) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Class,
                    contentDescription = "School & Foundation"
                )
            },
            label = { Text(AcademyTab.SCHOOL.title) },
            selectedContentColor = DuolingoGreen,
            unselectedContentColor = TextMuted
        )
    }
}
