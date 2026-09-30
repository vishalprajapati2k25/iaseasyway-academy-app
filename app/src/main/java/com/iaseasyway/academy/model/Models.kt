package com.iaseasyway.academy.model

enum class ExamCategory(val displayName: String, val shortBadge: String) {
    ALL("All Exams", "ALL"),
    UPSC_PRELIMS("UPSC Civil Services Pre", "UPSC"),
    MPSC_RAJYASEVA("MPSC Rajyaseva / State Service", "MPSC"),
    MPSC_COMBINED("MPSC Group B & C Combined", "MPSC Comb"),
    SSC_CGL("SSC CGL & CHSL", "SSC"),
    GROUP_C_D("Maharashtra Group C & D (Talathi/Police)", "Grp C/D"),
    STATE_PCS("State PCS (UPPSC, BPSC, MPPSC)", "State PCS"),
    SCHOLARSHIP_EXAM("4th & 5th Scholarship (शिष्यवृत्ती)", "Scholarship"),
    NAVODAYA_JNVST("5th Navodaya Vidyalaya (JNVST)", "Navodaya"),
    OLYMPIAD_EXAMS("School Olympiads (IMO / NSO / IEO)", "Olympiad"),
    SCHOOL_FOUNDATION("Class 5th - Graduation Hub", "School/Grad")
}

data class Course(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val price: Int,
    val discountPrice: Int,
    val instructor: String,
    val rating: Double,
    val totalStudents: Int,
    val isSubscribed: Boolean = false,
    val syllabusModules: List<String> = emptyList(),
    val badgeText: String = "Popular"
)

data class SubscriptionPlan(
    val id: String,
    val title: String,
    val price: Int,
    val billingCycle: String,
    val features: List<String>,
    val isPopular: Boolean = false
)

data class ExamPaper(
    val id: String,
    val title: String,
    val examCategory: ExamCategory,
    val year: Int,
    val targetClass: String,
    val durationMinutes: Int,
    val totalMarks: Int,
    val negativeMarking: Double,
    val questions: List<ExamQuestion>
)

data class ExamQuestion(
    val id: String,
    val number: Int,
    val text: String,
    val textMarathi: String? = null,
    val options: List<String>,
    val optionsMarathi: List<String>? = null,
    val correctOptionIndex: Int,
    val explanation: String,
    val pyqYear: String,
    val subject: String
)

data class ExamAttempt(
    val paperId: String,
    val answers: Map<Int, Int> = emptyMap(),
    val reviewMarked: Set<Int> = emptySet(),
    val timeSpentSeconds: Int = 0,
    val isSubmitted: Boolean = false
)

data class QuestionReview(
    val questionNumber: Int,
    val questionText: String,
    val selectedOption: Int?,
    val correctOption: Int,
    val isCorrect: Boolean,
    val explanation: String
)

data class ExamScoreResult(
    val paperId: String,
    val paperTitle: String,
    val totalQuestions: Int,
    val attemptedCount: Int,
    val correctCount: Int,
    val incorrectCount: Int,
    val unattemptedCount: Int,
    val rawScore: Double,
    val accuracyPercentage: Double,
    val percentileEstimate: Double,
    val timeTakenSeconds: Int,
    val reviews: List<QuestionReview>
)

enum class QuestionType {
    MCQ,
    TRUE_FALSE,
    MATCH_PAIRS,
    FILL_BLANK
}

data class GamifiedQuestion(
    val id: String,
    val type: QuestionType = QuestionType.MCQ,
    val prompt: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String,
    val mnemonic: String? = null
)

data class GamifiedStage(
    val id: String,
    val stageNumber: Int,
    val title: String,
    val xpReward: Int = 20,
    val isUnlocked: Boolean = false,
    val isCompleted: Boolean = false,
    val stars: Int = 0,
    val questions: List<GamifiedQuestion> = emptyList()
)

data class GamifiedLessonUnit(
    val id: String,
    val unitNumber: Int,
    val title: String,
    val topic: String,
    val stages: List<GamifiedStage>
)

data class UserProfile(
    val name: String = "IAS Aspirant",
    val email: String = "aspirant@iaseasyway.com",
    val currentGrade: String = "UPSC / Degree",
    val streakDays: Int = 7,
    val hearts: Int = 5,
    val maxHearts: Int = 5,
    val totalXp: Int = 1450,
    val subscribedCourses: Set<String> = setOf("course_upsc_prelims_2025"),
    val completedStages: Set<String> = setOf("stage_u1_s1", "stage_u1_s2")
)

data class SchoolGradeItem(
    val gradeName: String,
    val displayName: String,
    val keySubjects: List<String>,
    val totalLessons: Int,
    val foundationFocus: String
)
