# API_CONTRACT.md — IEW Academy REST API Specifications

This document defines the decoupled REST API contracts between the **IEW Academy Android Native Client** and backend endpoints.

---

## 1. Architectural Principles

1. **Sectional Decoupling**: Each module (Courses, Exams/PYQs, Gamified Play, School Hub, User Profile) operates on its own dedicated data contract. Schema modifications in the Course API do not affect the Exam Engine or Gamification pipelines.
2. **DRM & Anti-Scraping Headers**: Every request includes integrity verification headers:
   - `X-Client-Platform`: `Android-IEW-Native`
   - `X-Client-Version`: `1.0.0`
   - `X-DRM-Protection`: `FLAG_SECURE_R8`
   - `Authorization`: `Bearer <token>`
3. **Resilient Offline Cache**: If the remote server is unreachable, the client falls back seamlessly to the authenticated in-memory seed dataset.

---

## 2. WordPress Live Integration (`iaseasyway.com`)

### 2.1 Fetch Live Study Notes & News
* **Endpoint**: `GET https://www.iaseasyway.com/wp-json/wp/v2/posts`
* **Query Parameters**:
  - `per_page`: `10`
  - `_fields`: `id,title,excerpt,link,date`
* **Response**:
  ```json
  [
    {
      "id": 2969,
      "date": "2026-09-19T03:24:00",
      "link": "https://www.iaseasyway.com/daily-current-affairs",
      "title": { "rendered": "Daily Current Affairs for UPSC & MPSC" },
      "excerpt": { "rendered": "<p>Key editorial highlights and exam notes...</p>" }
    }
  ]
  ```

---

## 3. Academy Courses & Subscription API

### 3.1 List Courses
* **Endpoint**: `GET /api/v1/courses`
* **Response**:
  ```json
  {
    "courses": [
      {
        "id": "course_upsc_prelims_2025",
        "title": "UPSC IAS Prelims 2025 Super Intensive",
        "category": "UPSC",
        "price": 4999,
        "discountPrice": 1999,
        "instructor": "Prof. Prajapati & Team IAS EasyWay",
        "rating": 4.9,
        "totalStudents": 12450,
        "isSubscribed": false,
        "syllabusModules": [
          "Module 1: Indian Polity & Governance",
          "Module 2: Indian Modern History & Art & Culture"
        ]
      }
    ]
  }
  ```

### 3.2 Subscribe to Course
* **Endpoint**: `POST /api/v1/courses/{courseId}/subscribe`
* **Request**:
  ```json
  { "planId": "plan_annual_pro", "paymentMethod": "UPI" }
  ```
* **Response**:
  ```json
  { "status": "SUCCESS", "courseId": "course_upsc_prelims_2025", "accessExpiry": "2027-10-01" }
  ```

---

## 4. Timed Exam Simulation & PYQ API

### 4.1 List Exam Papers
* **Endpoint**: `GET /api/v1/exams?category=UPSC_PRELIMS`
* **Response**:
  ```json
  {
    "papers": [
      {
        "id": "upsc_prelims_2024_gs1",
        "title": "UPSC Civil Services Prelims 2024 GS-1",
        "year": 2024,
        "durationMinutes": 120,
        "totalMarks": 200,
        "negativeMarking": 0.66,
        "totalQuestions": 100
      }
    ]
  }
  ```

### 4.2 Fetch Full Question Paper (Protected)
* **Endpoint**: `GET /api/v1/exams/{paperId}`
* **Response**:
  ```json
  {
    "id": "upsc_prelims_2024_gs1",
    "questions": [
      {
        "id": "upsc_24_q1",
        "number": 1,
        "text": "With reference to the Constitution of India...",
        "textMarathi": "भारतीय संविधानाच्या संदर्भात...",
        "options": ["A. Maneka Gandhi", "B. Puttaswamy (2017)", "C. Gopalan", "D. Kesavananda"],
        "optionsMarathi": ["A. मनेका गांधी", "B. पुट्टास्वामी (२०१७)", "C. गोपालन", "D. केशवानंद"],
        "correctOptionIndex": 1,
        "explanation": "In Justice K.S. Puttaswamy v. UOI, a 9-judge bench ruled...",
        "pyqYear": "UPSC CSE 2024",
        "subject": "Indian Polity"
      }
    ]
  }
  ```

### 4.3 Submit Exam Attempt
* **Endpoint**: `POST /api/v1/exams/{paperId}/submit`
* **Request**:
  ```json
  {
    "answers": { "1": 1, "2": 3 },
    "timeSpentSeconds": 4820
  }
  ```
* **Response**:
  ```json
  {
    "rawScore": 142.5,
    "accuracyPercentage": 86.4,
    "percentileEstimate": 97.2,
    "rank": 312
  }
  ```

---

## 5. Duolingo Gamification Engine API

### 5.1 Fetch Progression Units
* **Endpoint**: `GET /api/v1/play/units`
* **Response**:
  ```json
  {
    "units": [
      {
        "id": "unit_1_polity",
        "unitNumber": 1,
        "title": "Constitution & Polity Blitz",
        "topic": "Fundamental Rights & DPSP",
        "stages": [
          {
            "id": "stage_u1_s1",
            "stageNumber": 1,
            "title": "Preamble & Fundamental Rights",
            "xpReward": 30,
            "isUnlocked": true,
            "isCompleted": true,
            "stars": 3
          }
        ]
      }
    ]
  }
  ```
