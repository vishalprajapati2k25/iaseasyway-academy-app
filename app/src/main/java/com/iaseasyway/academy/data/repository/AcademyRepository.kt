package com.iaseasyway.academy.data.repository

import com.iaseasyway.academy.data.api.ApiClient
import com.iaseasyway.academy.data.api.WordPressPost
import com.iaseasyway.academy.data.seed.SeedData
import com.iaseasyway.academy.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class AcademyRepository(private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)) {

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _courses = MutableStateFlow(SeedData.courses)
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _examPapers = MutableStateFlow(SeedData.examPapers)
    val examPapers: StateFlow<List<ExamPaper>> = _examPapers.asStateFlow()

    private val _gamifiedUnits = MutableStateFlow(SeedData.gamifiedUnits)
    val gamifiedUnits: StateFlow<List<GamifiedLessonUnit>> = _gamifiedUnits.asStateFlow()

    private val _liveArticles = MutableStateFlow<List<WordPressPost>>(emptyList())
    val liveArticles: StateFlow<List<WordPressPost>> = _liveArticles.asStateFlow()

    private val _editorials = MutableStateFlow(SeedData.editorials)
    val editorials: StateFlow<List<EditorialArticle>> = _editorials.asStateFlow()

    private val _memoryShortcuts = MutableStateFlow(SeedData.memoryShortcuts)
    val memoryShortcuts: StateFlow<List<MemoryShortcut>> = _memoryShortcuts.asStateFlow()

    private val _activeExamPaper = MutableStateFlow<ExamPaper?>(null)
    val activeExamPaper: StateFlow<ExamPaper?> = _activeExamPaper.asStateFlow()

    private val _activeExamAttempt = MutableStateFlow<ExamAttempt?>(null)
    val activeExamAttempt: StateFlow<ExamAttempt?> = _activeExamAttempt.asStateFlow()

    private val _examScoreResult = MutableStateFlow<ExamScoreResult?>(null)
    val examScoreResult: StateFlow<ExamScoreResult?> = _examScoreResult.asStateFlow()

    init {
        refreshLiveFeed()
    }

    fun refreshLiveFeed() {
        scope.launch {
            val result = ApiClient.fetchLatestArticles(6)
            if (result.isSuccess) {
                _liveArticles.value = result.getOrDefault(emptyList())
            }
        }
    }

    fun updateSelectedGrade(grade: String) {
        _userProfile.value = _userProfile.value.copy(currentGrade = grade)
    }

    fun subscribeCourse(courseId: String) {
        val currentSubscribed = _userProfile.value.subscribedCourses.toMutableSet().apply { add(courseId) }
        _userProfile.value = _userProfile.value.copy(subscribedCourses = currentSubscribed)
        _courses.value = _courses.value.map {
            if (it.id == courseId) it.copy(isSubscribed = true) else it
        }
    }

    fun deductHeart() {
        val current = _userProfile.value.hearts
        if (current > 0) {
            _userProfile.value = _userProfile.value.copy(hearts = current - 1)
        }
    }

    fun refillHearts() {
        _userProfile.value = _userProfile.value.copy(hearts = _userProfile.value.maxHearts)
    }

    fun completeGamifiedStage(unitId: String, stageId: String, stars: Int, xpBonus: Int) {
        val currentProfile = _userProfile.value
        val updatedCompleted = currentProfile.completedStages.toMutableSet().apply { add(stageId) }
        _userProfile.value = currentProfile.copy(
            totalXp = currentProfile.totalXp + xpBonus,
            streakDays = if (currentProfile.streakDays == 0) 1 else currentProfile.streakDays,
            completedStages = updatedCompleted
        )

        // Unlock next stage in unit
        _gamifiedUnits.value = _gamifiedUnits.value.map { unit ->
            if (unit.id == unitId) {
                val stages = unit.stages.mapIndexed { idx, stage ->
                    if (stage.id == stageId) {
                        stage.copy(isCompleted = true, stars = maxOf(stage.stars, stars))
                    } else if (idx > 0 && unit.stages[idx - 1].id == stageId) {
                        stage.copy(isUnlocked = true)
                    } else {
                        stage
                    }
                }
                unit.copy(stages = stages)
            } else {
                unit
            }
        }
    }

    fun startExam(paperId: String) {
        val paper = _examPapers.value.find { it.id == paperId } ?: _examPapers.value.first()
        _activeExamPaper.value = paper
        _activeExamAttempt.value = ExamAttempt(paperId = paper.id)
        _examScoreResult.value = null
    }

    fun selectExamAnswer(questionNumber: Int, optionIndex: Int) {
        val current = _activeExamAttempt.value ?: return
        val updatedAnswers = current.answers.toMutableMap().apply { put(questionNumber, optionIndex) }
        _activeExamAttempt.value = current.copy(answers = updatedAnswers)
    }

    fun clearExamAnswer(questionNumber: Int) {
        val current = _activeExamAttempt.value ?: return
        val updatedAnswers = current.answers.toMutableMap().apply { remove(questionNumber) }
        _activeExamAttempt.value = current.copy(answers = updatedAnswers)
    }

    fun toggleExamReview(questionNumber: Int) {
        val current = _activeExamAttempt.value ?: return
        val updatedReview = current.reviewMarked.toMutableSet().apply {
            if (contains(questionNumber)) remove(questionNumber) else add(questionNumber)
        }
        _activeExamAttempt.value = current.copy(reviewMarked = updatedReview)
    }

    fun submitExam(): ExamScoreResult? {
        val paper = _activeExamPaper.value ?: return null
        val attempt = _activeExamAttempt.value ?: return null

        var correctCount = 0
        var incorrectCount = 0
        val reviews = mutableListOf<QuestionReview>()

        for (q in paper.questions) {
            val selected = attempt.answers[q.number]
            val isCorrect = selected != null && selected == q.correctOptionIndex
            if (selected != null) {
                if (isCorrect) correctCount++ else incorrectCount++
            }
            reviews.add(
                QuestionReview(
                    questionNumber = q.number,
                    questionText = q.text,
                    selectedOption = selected,
                    correctOption = q.correctOptionIndex,
                    isCorrect = isCorrect,
                    explanation = q.explanation
                )
            )
        }

        val total = paper.questions.size
        val unattemptedCount = total - (correctCount + incorrectCount)
        val marksPerQuestion = (paper.totalMarks.toDouble() / total)
        val rawScore = (correctCount * marksPerQuestion) - (incorrectCount * paper.negativeMarking)
        val finalScore = maxOf(0.0, ((rawScore * 100).roundToInt() / 100.0))
        val accuracy = if ((correctCount + incorrectCount) > 0) {
            ((correctCount.toDouble() / (correctCount + incorrectCount)) * 1000.0).roundToInt() / 10.0
        } else {
            0.0
        }

        // Realistic estimated percentile
        val percentile = when {
            accuracy >= 90.0 -> 99.4
            accuracy >= 80.0 -> 96.8
            accuracy >= 70.0 -> 91.2
            accuracy >= 60.0 -> 84.5
            accuracy >= 50.0 -> 72.0
            else -> 48.0
        }

        val result = ExamScoreResult(
            paperId = paper.id,
            paperTitle = paper.title,
            totalQuestions = total,
            attemptedCount = correctCount + incorrectCount,
            correctCount = correctCount,
            incorrectCount = incorrectCount,
            unattemptedCount = unattemptedCount,
            rawScore = finalScore,
            accuracyPercentage = accuracy,
            percentileEstimate = percentile,
            timeTakenSeconds = attempt.timeSpentSeconds,
            reviews = reviews
        )

        _examScoreResult.value = result
        _activeExamAttempt.value = attempt.copy(isSubmitted = true)

        // Award XP for taking full exam
        val profile = _userProfile.value
        _userProfile.value = profile.copy(totalXp = profile.totalXp + 100)

        return result
    }
}
