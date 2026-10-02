# IEW Academy — Android Native Application (`iaseasyway-academy-app`)

[![Android CI & Policy Audit](https://github.com/vishalprajapati2k25/iaseasyway-academy-app/actions/workflows/android-ci.yml/badge.svg)](https://github.com/vishalprajapati2k25/iaseasyway-academy-app/actions)
![Target SDK](https://img.shields.io/badge/Target%20SDK-35%20(Android%2015)-green)
![Min SDK](https://img.shields.io/badge/Min%20SDK-24-blue)
![Architecture](https://img.shields.io/badge/Architecture-MVVM%20%2B%20Clean-orange)
![Security](https://img.shields.io/badge/DRM-FLAG__SECURE%20%2B%20R8-red)
[![GitHub release](https://img.shields.io/github/v/release/vishalprajapati2k25/iaseasyway-academy-app?color=blue&logo=github)](https://github.com/vishalprajapati2k25/iaseasyway-academy-app/releases)

---

## 📥 Direct Downloads & Official Releases (GitHub CDN)

| Build Artifact | Format | Size | Direct Download Link | Target / Purpose |
| :--- | :--- | :---: | :--- | :--- |
| **`IEWAcademy-v1.0.0-release.apk`** | Signed APK | **5.5 MB** | [⬇️ **Download Release APK**](https://github.com/vishalprajapati2k25/iaseasyway-academy-app/releases/download/v1.0.0/IEWAcademy-v1.0.0-release.apk) | Production testing, direct Android phone installation & sideloading. |
| **`IEWAcademy-v1.0.0-debug.apk`** | Debug APK | **15.0 MB** | [⬇️ **Download Debug APK**](https://github.com/vishalprajapati2k25/iaseasyway-academy-app/releases/download/v1.0.0/IEWAcademy-v1.0.0-debug.apk) | Developer testing with Logcat and diagnostics enabled. |
| **`IEWAcademy-v1.0.0-release.aab`** | App Bundle | **16.3 MB** | [⬇️ **Download Production AAB**](https://github.com/vishalprajapati2k25/iaseasyway-academy-app/releases/download/v1.0.0/IEWAcademy-v1.0.0-release.aab) | Google Play Console store submission package. |

> 🏷️ **GitHub Release Tag**: [`v1.0.0`](https://github.com/vishalprajapati2k25/iaseasyway-academy-app/releases/tag/v1.0.0) • [Release Notes & Checksums](file:///home/virus/.gemini/antigravity-cli/scratch/iaseasyway-academy-app/release/README.md)

---

**IEW Academy** is the official Android native learning and exam simulation app for **IAS EasyWay** ([iaseasyway.com](https://www.iaseasyway.com)). It combines **Duolingo-style gamified learning** with a **time-based examination platform** covering all competitive exams (UPSC, MPSC, SSC, Group C/D, Maharashtra & other State PCS) and school foundation levels from **Class 5th till Graduation**.

---

## 🌟 Key Highlights & Capabilities

### 1. 🎓 Academy Course Subscriptions
- Students can browse, preview syllabus modules, and subscribe to premium academy coaching programs:
  - **UPSC IAS Prelims Super Intensive Batch**
  - **MPSC Rajyaseva & Combined Group B/C Integrated Batch**
  - **SSC CGL & CHSL Complete Quant & Reasoning Batch**
  - **Maharashtra Group C & D (Talathi, Police Bharti, Clerk) Batch**
  - **School IAS Foundation Club (Class 5th - 12th)**
- Flexible subscription passes: Monthly, Annual Pro, and Lifetime Rankers Club.

### 2. 🎮 Duolingo-Style Play ("Play to Learn")
- **Stepping Stone Learning Path**: Visual zigzag progression path with unlocked, in-progress, and completed nodes.
- **Streaks & XP**: Daily burning streaks (🔥) and experience points (⚡) to gamify daily habit formation.
- **Hearts / Lives**: 5 Hearts system (❤️) that reinforces careful reasoning and revision.
- **Interactive Question Types**: Rapid MCQs, True/False, and Match-Pairs with instant feedback, celebratory sound cues, and memory mnemonics.

### 3. 🏫 Class 5th till Graduation Foundation Hub
- Dedicated personalized roadmaps for:
  - **Class 5th - 8th**: NCERT Science, Earth Systems, Early History timelines, and Environmental studies.
  - **Class 9th - 10th**: Democratic Politics, Constitution, Physical Geography, Motion & Economics.
  - **Class 11th - 12th**: Advanced NCERT analysis, Harappan archaeology, Macroeconomics, and Editorial reading.
  - **Graduation / Degree Track**: Direct civil services exam drill down.

### 4. ⏱️ Real-Time Exam Simulation Platform & PYQ Bank
- Exact replica of computerized examination environments:
  - **UPSC Civil Services Prelims (GS 1 & CSAT Paper 2)**
  - **MPSC Rajyaseva & Combined Group B & C (Maharashtra State)**
  - **SSC CGL & CHSL Tier-1 & Tier-2**
  - **Maharashtra Group C (Talathi, Clerk) & Group D (Police Bharti, Railway RRB)**
  - **State PCS (UPPSC, BPSC, MPPSC)**
- **Features**:
  - Live countdown timer with negative marking calculation (-0.66, -0.50, -0.25).
  - Bilingual Question Toggle (**English ⇄ मराठी**) for authentic Maharashtra state exams.
  - Interactive **Question Palette** showing Answered (Green), Unanswered (Red), Marked for Review (Purple), and Not Visited (Grey).
  - Detailed scorecard with accuracy percentage, All-India/State percentile estimate, and pedagogical solutions.

### 5. 🛡️ Content Security & Anti-Scraping DRM
- **Screenshot & Recording Blocker**: Enforces hardware-backed `WindowManager.LayoutParams.FLAG_SECURE` across all sensitive screens. System screenshot shortcuts, screen casting, and background app switchers are completely blocked.
- **Decoupled API-Driven Architecture**: Questions and solutions are streamed dynamically from authenticated REST APIs with token validation. No plaintext question assets exist in the APK for scrapers or decompilers to extract.
- **R8 Obfuscation & Anti-Recompilation**: Full ProGuard/R8 optimizations, package flattening, class renaming, and debug stripping protect proprietary code logic.

---

## 🏗️ Technical Architecture

```
com.iaseasyway.academy
├── data
│   ├── api          # ApiClient for iaseasyway.com WordPress API & Academy REST
│   ├── repository   # AcademyRepository (StateFlow, reactive state, business logic)
│   ├── security     # SecurityManager (FLAG_SECURE, Root & Debugger checks)
│   └── seed         # SeedData (High-fidelity offline fallback question bank)
├── model            # Immutable DTOs (Course, ExamPaper, GamifiedUnit, UserProfile)
└── ui
    ├── components   # AcademyTopBar, BottomBar, QuestionPalette, ClassSelector
    ├── screens      # DuolingoLearn, ExamSimulation, ExamResult, Courses, SchoolHub, Profile
    └── theme        # IAS EasyWay Navy, Gold & Duolingo Emerald Color Schemes
```

---

## 🚀 Building & Running the Project

### Prerequisites
- JDK 17 (e.g. `/opt/android-studio/jbr`)
- Android SDK with API 35 installed (`/home/virus/Android/Sdk`)

### Compile and Verify
```bash
# Set environment
export JAVA_HOME=/opt/android-studio/jbr
export ANDROID_HOME=/home/virus/Android/Sdk

# Run Lint
./gradlew lintDebug

# Assemble Debug APK
./gradlew assembleDebug

# Assemble Release APK (obfuscated & optimized)
./gradlew assembleRelease
```
