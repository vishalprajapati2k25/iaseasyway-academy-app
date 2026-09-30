package com.iaseasyway.academy.data

import com.iaseasyway.academy.data.repository.AcademyRepository
import com.iaseasyway.academy.model.ExamCategory
import com.iaseasyway.academy.model.NewspaperSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AcademyRepositoryTest {

    private lateinit var testScope: TestScope
    private lateinit var repository: AcademyRepository

    @Before
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()
        testScope = TestScope(testDispatcher)
        repository = AcademyRepository(testScope)
    }

    @Test
    fun testInitialState() {
        val profile = repository.userProfile.value
        assertEquals("IAS Aspirant", profile.name)
        assertEquals(5, profile.hearts)
        assertEquals(5, profile.maxHearts)
        assertTrue(profile.streakDays >= 1)
        assertEquals("UPSC / Degree", profile.currentGrade)

        assertFalse(repository.courses.value.isEmpty())
        assertFalse(repository.examPapers.value.isEmpty())
        assertFalse(repository.gamifiedUnits.value.isEmpty())
        assertFalse(repository.editorials.value.isEmpty())
        assertFalse(repository.memoryShortcuts.value.isEmpty())
    }

    @Test
    fun testCourseSubscription() {
        val firstCourse = repository.courses.value.first()
        val courseId = firstCourse.id

        repository.subscribeCourse(courseId)

        val updatedProfile = repository.userProfile.value
        assertTrue(updatedProfile.subscribedCourses.contains(courseId))

        val updatedCourse = repository.courses.value.find { it.id == courseId }
        assertNotNull(updatedCourse)
        assertTrue(updatedCourse!!.isSubscribed)
    }

    @Test
    fun testHeartDeductionAndRefill() {
        assertEquals(5, repository.userProfile.value.hearts)

        repository.deductHeart()
        assertEquals(4, repository.userProfile.value.hearts)

        repository.deductHeart()
        assertEquals(3, repository.userProfile.value.hearts)

        repository.refillHearts()
        assertEquals(5, repository.userProfile.value.hearts)
    }

    @Test
    fun testGamifiedStageCompletion() {
        val initialXp = repository.userProfile.value.totalXp
        val unit = repository.gamifiedUnits.value.first()
        val stage = unit.stages.first()

        repository.completeGamifiedStage(
            unitId = unit.id,
            stageId = stage.id,
            stars = 3,
            xpBonus = 50
        )

        val updatedProfile = repository.userProfile.value
        assertEquals(initialXp + 50, updatedProfile.totalXp)
        assertTrue(updatedProfile.completedStages.contains(stage.id))

        val updatedUnit = repository.gamifiedUnits.value.find { it.id == unit.id }!!
        val updatedStage = updatedUnit.stages.find { it.id == stage.id }!!
        assertTrue(updatedStage.isCompleted)
        assertEquals(3, updatedStage.stars)

        if (updatedUnit.stages.size > 1) {
            assertTrue(updatedUnit.stages[1].isUnlocked)
        }
    }

    @Test
    fun testExamLifecycleAndScoring() {
        val upscPaper = repository.examPapers.value.find { it.id == "upsc_prelims_2024_gs1" }
        assertNotNull("UPSC Paper must exist", upscPaper)

        repository.startExam(upscPaper!!.id)
        assertNotNull(repository.activeExamPaper.value)
        assertNotNull(repository.activeExamAttempt.value)

        val q1 = upscPaper.questions[0]
        val q2 = upscPaper.questions[1]

        // Q1: Answer correctly
        repository.selectExamAnswer(q1.number, q1.correctOptionIndex)
        // Q2: Answer incorrectly
        val wrongOptionIndex = (q2.correctOptionIndex + 1) % q2.options.size
        repository.selectExamAnswer(q2.number, wrongOptionIndex)
        // Mark Q2 for review
        repository.toggleExamReview(q2.number)

        val attempt = repository.activeExamAttempt.value!!
        assertEquals(2, attempt.answers.size)
        assertTrue(attempt.reviewMarked.contains(q2.number))

        // Submit exam
        val result = repository.submitExam()
        assertNotNull(result)
        assertEquals(1, result!!.correctCount)
        assertEquals(1, result.incorrectCount)
        assertEquals(upscPaper.questions.size - 2, result.unattemptedCount)
        assertEquals(50.0, result.accuracyPercentage, 0.1)
        assertTrue(result.rawScore >= 0.0)
        assertNotNull(repository.examScoreResult.value)
        assertTrue(repository.activeExamAttempt.value!!.isSubmitted)
    }

    @Test
    fun testAllExamCategoriesPresent() {
        val papers = repository.examPapers.value
        val categories = papers.map { it.examCategory }.toSet()

        assertTrue(categories.contains(ExamCategory.UPSC_PRELIMS))
        assertTrue(categories.contains(ExamCategory.MPSC_RAJYASEVA))
        assertTrue(categories.contains(ExamCategory.SSC_CGL))
        assertTrue(categories.contains(ExamCategory.GROUP_C_D))
        assertTrue(categories.contains(ExamCategory.SCHOLARSHIP_EXAM))
        assertTrue(categories.contains(ExamCategory.NAVODAYA_JNVST))
        assertTrue(categories.contains(ExamCategory.OLYMPIAD_EXAMS))

        // Ensure 4th/5th scholarship, 5th navodaya, and olympiad papers exist
        val scholarship = papers.find { it.id == "scholarship_4th_5th_2024" }
        assertNotNull("4th/5th Scholarship paper must be present", scholarship)
        assertTrue(scholarship!!.questions.isNotEmpty())

        val navodaya = papers.find { it.id == "navodaya_jnvst_class6_2024" }
        assertNotNull("5th Navodaya paper must be present", navodaya)
        assertTrue(navodaya!!.questions.isNotEmpty())

        val olympiad = papers.find { it.id == "olympiad_imo_nso_2024" }
        assertNotNull("Olympiad paper must be present", olympiad)
        assertTrue(olympiad!!.questions.isNotEmpty())
    }

    @Test
    fun testEditorialsAndReversePsychologyMnemonics() {
        val editorials = repository.editorials.value
        assertFalse(editorials.isEmpty())

        // Verify coverage of The Hindu, Loksatta, and The Indian Express
        val sources = editorials.map { it.newspaper }.toSet()
        assertTrue(sources.contains(NewspaperSource.THE_HINDU))
        assertTrue(sources.contains(NewspaperSource.LOKSATTA))
        assertTrue(sources.contains(NewspaperSource.INDIAN_EXPRESS))

        editorials.forEach { article ->
            assertTrue(article.summary3Min.isNotEmpty())
            assertTrue(article.keyTakeaways.isNotEmpty())
            assertTrue(article.syllabusTopics.isNotEmpty())
            assertTrue(article.mainsModelFramework.isNotEmpty())
            assertNotNull(article.probablePrelimsQuestion)
            assertNotNull("Each editorial should have a memory shortcut", article.memoryShortcut)
            assertTrue(article.memoryShortcut!!.reversePsychologyHook.isNotEmpty())
        }

        // Verify standalone memory shortcuts
        val shortcuts = repository.memoryShortcuts.value
        assertFalse(shortcuts.isEmpty())
        val shortcutCodes = shortcuts.map { it.mnemonicCode }
        assertTrue(shortcutCodes.contains("E - F - E - R - C - C"))
        assertTrue(shortcutCodes.contains("GARAM CHAI JALEBI BANO TASTY MAZA"))
        assertTrue(shortcutCodes.any { it.contains("K-SA-MI-HA-SA-TO") })

        // Verify editorial memory shortcuts
        val editorialShortcuts = editorials.mapNotNull { it.memoryShortcut }
        assertEquals(3, editorialShortcuts.size)
        assertTrue(editorialShortcuts.all { it.reversePsychologyHook.isNotEmpty() && it.mnemonicCode.isNotEmpty() })
    }
}
