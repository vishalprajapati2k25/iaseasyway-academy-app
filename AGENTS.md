# AGENTS.md — Operational Protocol for IEW Academy Android Native App

This document governs agentic and human pair-programming operations within the **IEW Academy Android Native App** (`iaseasyway-academy-app`) repository.

---

## 1. Repository Identity & Topology

| Component | Target / Value |
|---|---|
| **Android App Repository** | `iaseasyway-academy-app` |
| **Website Platform** | `https://www.iaseasyway.com` |
| **Live WordPress REST API** | `https://www.iaseasyway.com/wp-json/wp/v2` |
| **Academy API Base** | `https://api.iaseasyway.com/v1` |
| **Target SDK / Android API** | Android 15 (API 35) |
| **Min SDK** | Android 7.0 (API 24) |
| **Language & UI Framework** | Kotlin 2.2+ with Jetpack Compose & Material 3 |
| **Application ID** | `com.iaseasyway.academy` |

---

## 2. Core Operational & Compliance Rules (Non-Negotiable)

1. **Hardware-Backed Content Protection (DRM)**:
   - All activities displaying examination questions, Duolingo lesson stages, or academy course materials MUST enforce `WindowManager.LayoutParams.FLAG_SECURE`.
   - Never allow screenshots, screen scraping, or window thumbnail caching of proprietary questions.
2. **API-Driven Architecture (No Plaintext Asset Scraping)**:
   - Exam questions and course content MUST be fetched dynamically via decoupled REST API endpoints with auth tokens.
   - Seed data is strictly an offline fallback cache. Any schema modification must preserve cross-sectional isolation: modifying the Course API must never break the Exam Engine or Duolingo modules.
3. **Keep Secrets & Signing Keys Out of Git**:
   - `local.properties`, `*.jks`, and `*.keystore` MUST remain in `.gitignore`.
   - Release signing must be conditional on keystore existence.
4. **Android 15 (SDK 35) & 64-bit Compliance**:
   - Must strictly satisfy Google Play Developer Console requirements (compileSdk 35, targetSdk 35, 64-bit libraries).
5. **Dark & Light Mode Parity**:
   - All Compose components must maintain high readability and contrast in both Light and Dark themes.

---

## 3. Directory Layout

```
iaseasyway-academy-app/
├── .github/workflows/
│   └── android-ci.yml             # GitHub Actions CI for linting and debug build
├── app/
│   ├── build.gradle               # App-level build config (SDK 35, Jetpack Compose)
│   ├── proguard-rules.pro         # Proguard/R8 optimization & anti-recompilation rules
│   └── src/main/
│       ├── AndroidManifest.xml    # Permissions, Application declaration
│       └── java/com/iaseasyway/academy/
│           ├── MainActivity.kt    # Root Compose host with FLAG_SECURE
│           ├── IEWAcademyApplication.kt
│           ├── data/
│           │   ├── api/           # ApiClient (iaseasyway.com & IEW Academy REST)
│           │   ├── repository/    # AcademyRepository (StateFlow, cache, business logic)
│           │   ├── security/      # SecurityManager (FLAG_SECURE, root & debugger detection)
│           │   └── seed/          # Resilient offline seed questions (UPSC, MPSC, SSC, School)
│           ├── model/
│           │   └── Models.kt      # DTOs & domain models (ExamPaper, Course, Duolingo, User)
│           └── ui/
│               ├── components/    # TopBar, BottomBar, QuestionPalette, ClassSelector
│               ├── screens/       # DuolingoPlay, ExamSimulation, ExamResult, Courses, SchoolHub, Profile
│               └── theme/         # Color, Shape, Theme
├── store-assets/                  # Google Play compliant 512x512 icon & 1024x500 feature graphic
├── API_CONTRACT.md                # REST API contract for all dynamic sections
├── README.md                      # Public documentation
├── AGENTS.md                      # This operational protocol
├── settings.gradle                # Gradle settings
├── build.gradle                   # Root build script
├── gradle.properties              # Memory and JVM configuration
├── local.properties.example       # Development template
└── gradlew                        # Gradle wrapper script
```

---

## 4. Local Build & Test Commands

* **Set Up Java & SDK Environment**:
  ```bash
  export JAVA_HOME=/opt/android-studio/jbr
  export ANDROID_HOME=/home/virus/Android/Sdk
  ```
* **Assemble Debug APK**:
  ```bash
  ./gradlew assembleDebug
  ```
* **Run Lint**:
  ```bash
  ./gradlew lintDebug
  ```
* **Assemble Signed Release APK**:
  ```bash
  ./gradlew assembleRelease
  ```

---

## 5. Change Ledger

- **v1.0.0 (Initial Release Setup)**:
  - Initialized repository based on Google Play Store compliance baseline (SDK 35, 64-bit, R8 obfuscation).
  - Integrated `FLAG_SECURE` hardware screenshot blocker.
  - Implemented Duolingo-style gamified learning ("Play to Learn") with streaks, hearts, and XP.
  - Implemented real-time exam simulation platform with countdown timer, question palette, bilingual Marathi/English toggle, and negative marking calculation.
  - Added School Foundation hub for Class 5th to Graduation.
  - Added Academy Course subscription system with syllabus previews.
  - Connected live REST API feed from `iaseasyway.com` WordPress endpoint.
