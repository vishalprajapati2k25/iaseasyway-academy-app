package com.iaseasyway.academy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.iaseasyway.academy.data.security.SecurityManager
import com.iaseasyway.academy.ui.components.*
import com.iaseasyway.academy.ui.screens.*
import com.iaseasyway.academy.ui.theme.IEWAcademyTheme
import com.iaseasyway.academy.ui.theme.Navy900
import com.iaseasyway.academy.ui.theme.PureWhite

enum class ActiveScreen {
    MAIN_TABS,
    EXAM_SIMULATION,
    EXAM_RESULT,
    PROFILE_SCREEN
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // HARDWARE-LEVEL SCREENSHOT & SCREEN RECORDING PREVENTION
        // Blocks screencasting, system screenshot shortcuts, and recent task caching
        SecurityManager.applyScreenshotProtection(this)

        val repository = (application as IEWAcademyApplication).repository

        setContent {
            IEWAcademyTheme {
                val userProfile by repository.userProfile.collectAsState()

                var currentTab by remember { mutableStateOf(AcademyTab.LEARN) }
                var activeScreen by remember { mutableStateOf(ActiveScreen.MAIN_TABS) }
                var showClassSelector by remember { mutableStateOf(false) }

                when (activeScreen) {
                    ActiveScreen.MAIN_TABS -> {
                        Scaffold(
                            topBar = {
                                AcademyTopBar(
                                    userProfile = userProfile,
                                    onClassClick = { showClassSelector = true },
                                    onRefillHeartsClick = { repository.refillHearts() },
                                    onProfileClick = { activeScreen = ActiveScreen.PROFILE_SCREEN }
                                )
                            },
                            bottomBar = {
                                AcademyBottomBar(
                                    currentTab = currentTab,
                                    onTabSelected = { currentTab = it }
                                )
                            }
                        ) { paddingValues ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                            ) {
                                when (currentTab) {
                                    AcademyTab.LEARN -> {
                                        DuolingoLearnScreen(repository = repository)
                                    }
                                    AcademyTab.EDITORIALS -> {
                                        CurrentAffairsHubScreen(
                                            repository = repository,
                                            onStartExamFromTopic = { paperId ->
                                                repository.startExam(paperId)
                                                activeScreen = ActiveScreen.EXAM_SIMULATION
                                            }
                                        )
                                    }
                                    AcademyTab.EXAMS -> {
                                        ExamHubScreen(
                                            repository = repository,
                                            onStartExam = { paperId ->
                                                repository.startExam(paperId)
                                                activeScreen = ActiveScreen.EXAM_SIMULATION
                                            }
                                        )
                                    }
                                    AcademyTab.COURSES -> {
                                        CoursesCatalogScreen(repository = repository)
                                    }
                                    AcademyTab.SCHOOL -> {
                                        SchoolClassHubScreen(
                                            repository = repository,
                                            onGradeSelected = {
                                                // Updated in repo
                                            },
                                            onStartExamFromGrade = { gradeName ->
                                                val targetPaperId = when {
                                                    gradeName.contains("4th") || gradeName.contains("Scholarship") -> "scholarship_4th_5th_2024"
                                                    gradeName.contains("5th") || gradeName.contains("Navodaya") -> "navodaya_jnvst_class6_2024"
                                                    gradeName.contains("Olympiad") -> "olympiad_imo_nso_2024"
                                                    gradeName.contains("MPSC") -> "mpsc_rajyaseva_2024"
                                                    gradeName.contains("SSC") -> "ssc_cgl_tier1_2024"
                                                    gradeName.contains("Group C") || gradeName.contains("Talathi") -> "group_c_talathi_2024"
                                                    else -> "upsc_prelims_2024_gs1"
                                                }
                                                repository.startExam(targetPaperId)
                                                activeScreen = ActiveScreen.EXAM_SIMULATION
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    ActiveScreen.EXAM_SIMULATION -> {
                        ExamSimulationScreen(
                            repository = repository,
                            onExamFinished = {
                                activeScreen = ActiveScreen.EXAM_RESULT
                            },
                            onExitExam = {
                                activeScreen = ActiveScreen.MAIN_TABS
                            }
                        )
                    }

                    ActiveScreen.EXAM_RESULT -> {
                        ExamResultScreen(
                            repository = repository,
                            onBackToHub = {
                                currentTab = AcademyTab.EXAMS
                                activeScreen = ActiveScreen.MAIN_TABS
                            }
                        )
                    }

                    ActiveScreen.PROFILE_SCREEN -> {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Text(
                                            text = "Aspirant Profile & Security",
                                            color = PureWhite,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = { activeScreen = ActiveScreen.MAIN_TABS }) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = "Back",
                                                tint = PureWhite
                                            )
                                        }
                                    },
                                    backgroundColor = Navy900
                                )
                            }
                        ) { paddingValues ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                            ) {
                                ProfileScreen(repository = repository)
                            }
                        }
                    }
                }

                // Global Class / Grade Switcher Dialog
                if (showClassSelector) {
                    ClassSelectorDialog(
                        currentGrade = userProfile.currentGrade,
                        onGradeSelected = { newGrade ->
                            repository.updateSelectedGrade(newGrade)
                            showClassSelector = false
                        },
                        onDismiss = { showClassSelector = false }
                    )
                }
            }
        }
    }
}
