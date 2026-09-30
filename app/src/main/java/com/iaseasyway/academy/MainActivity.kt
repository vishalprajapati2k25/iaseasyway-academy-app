package com.iaseasyway.academy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.iaseasyway.academy.data.security.SecurityManager
import com.iaseasyway.academy.ui.components.*
import com.iaseasyway.academy.ui.screens.*
import com.iaseasyway.academy.ui.theme.IEWAcademyTheme

enum class ActiveScreen {
    MAIN_TABS,
    EXAM_SIMULATION,
    EXAM_RESULT
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
                                    onRefillHeartsClick = { repository.refillHearts() }
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
                                            onStartExamFromGrade = { _ ->
                                                // Start appropriate paper
                                                repository.startExam("upsc_prelims_2024_gs1")
                                                activeScreen = ActiveScreen.EXAM_SIMULATION
                                            }
                                        )
                                    }
                                    AcademyTab.PROFILE -> {
                                        ProfileScreen(repository = repository)
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
