package com.iaseasyway.academy.data.seed

import com.iaseasyway.academy.model.*

object SeedData {

    val courses: List<Course> = listOf(
        Course(
            id = "course_upsc_prelims_2025",
            title = "UPSC IAS Prelims 2025 Super Intensive",
            subtitle = "Complete GS 1 + CSAT with 15 Years PYQ Breakdown & Daily Gamified Practice",
            category = "UPSC",
            price = 4999,
            discountPrice = 1999,
            instructor = "Prof. Prajapati & Team IAS EasyWay",
            rating = 4.9,
            totalStudents = 12450,
            isSubscribed = true,
            badgeText = "Best Seller",
            syllabusModules = listOf(
                "Module 1: Indian Polity & Governance (Articles 1-395)",
                "Module 2: Indian Modern History & Art & Culture",
                "Module 3: Physical & Human Geography with Map Work",
                "Module 4: Indian Economy, Budget & Economic Survey",
                "Module 5: Environment, Biodiversity & Climate Change",
                "Module 6: Science & Tech, AI, Space & Defense",
                "Module 7: CSAT Comprehension, Math & Reasoning"
            )
        ),
        Course(
            id = "course_mpsc_rajyaseva_2025",
            title = "MPSC Rajyaseva & Combine Group B/C 2025",
            subtitle = "Maharashtra Special GS + Language (Marathi/English) + PYQ Mastery",
            category = "MPSC",
            price = 3999,
            discountPrice = 1499,
            instructor = "Bhagyashri Madam & MPSC Mentors",
            rating = 4.95,
            totalStudents = 18920,
            isSubscribed = false,
            badgeText = "Most Popular in MH",
            syllabusModules = listOf(
                "घटक १: महाराष्ट्राचा भूगोल, नद्या व वने",
                "घटक २: महाराष्ट्राचा आधुनिक इतिहास व समाजसुधारक",
                "घटक ३: भारतीय राज्यघटना व पंचायत राज (७३वी व ७४वी घटनादुरुस्ती)",
                "घटक ४: सामान्य विज्ञान, चालू घडामोडी व सांख्यिकी",
                "घटक ५: मराठी व्याकरण व इंग्रजी व्याकरण सराव"
            )
        ),
        Course(
            id = "course_ssc_cgl_chsl",
            title = "SSC CGL / CHSL 2025 All-in-One Super Batch",
            subtitle = "Maths Shortcuts, English Vocab, General Awareness & Tier-1/2 Simulator",
            category = "SSC",
            price = 2999,
            discountPrice = 999,
            instructor = "SSC Top Rankers Panel",
            rating = 4.8,
            totalStudents = 9540,
            isSubscribed = false,
            badgeText = "High Selection Rate",
            syllabusModules = listOf(
                "Section 1: Quantitative Aptitude Speed Drills",
                "Section 2: General Intelligence & Logical Reasoning",
                "Section 3: English Comprehension & Error Detection",
                "Section 4: Static GK & 10-Year SSC PYQ Bank"
            )
        ),
        Course(
            id = "course_group_cd_talathi",
            title = "Maharashtra Group C & D (Talathi, Police Bharti, Clerk)",
            subtitle = "TCS / IBPS Pattern Test Sets, Marathi Grammar, Maths & GK",
            category = "Group C/D",
            price = 1999,
            discountPrice = 699,
            instructor = "IEW Academy State Exam Cell",
            rating = 4.85,
            totalStudents = 24100,
            isSubscribed = false,
            badgeText = "IBPS/TCS Pattern",
            syllabusModules = listOf(
                "विभाग १: टीसीएस / आयबीपीएस पॅटर्न सराव प्रश्नपत्रिका",
                "विभाग २: संपूर्ण मराठी व्याकरण व शब्दसंग्रह",
                "विभाग ३: सामान्य ज्ञान (महाराष्ट्र विशेष व चालू घडामोडी)",
                "विभाग ४: अंकगणित व बुद्धिमत्ता चाचणी क्लृप्त्या"
            )
        ),
        Course(
            id = "course_school_ias_foundation",
            title = "School IAS Foundation Club (Class 5th - 12th)",
            subtitle = "Build Civil Services Thinking from School with NCERT Gamified Lessons",
            category = "School Foundation",
            price = 2499,
            discountPrice = 899,
            instructor = "IEW Young Scholars Academy",
            rating = 4.9,
            totalStudents = 7800,
            isSubscribed = false,
            badgeText = "Future Officers",
            syllabusModules = listOf(
                "Class 5-8: Basic Science, Earth Systems & History Stories",
                "Class 9-10: Constitution Basics, Geography Maps & Critical Logic",
                "Class 11-12: Advanced NCERT Mastery & Newspaper Reading Skills"
            )
        )
    )

    val subscriptionPlans: List<SubscriptionPlan> = listOf(
        SubscriptionPlan(
            id = "plan_monthly",
            title = "Monthly Aspirant",
            price = 299,
            billingCycle = "per month",
            features = listOf(
                "Full Access to Duolingo-style Learning Play",
                "Unlimited Class 5th - Graduation quizzes",
                "10 Mock Tests per month with Timer",
                "Basic Performance Scorecard"
            ),
            isPopular = false
        ),
        SubscriptionPlan(
            id = "plan_annual_pro",
            title = "Annual Officer Pass",
            price = 1499,
            billingCycle = "per year",
            features = listOf(
                "Unlimited All Courses & Masterclasses",
                "Duolingo Play: Unlimited Hearts & XP Boosters",
                "All Years PYQ Bank (UPSC, MPSC, SSC, Group C/D)",
                "Real Exam Simulator with Negative Marking & Percentiles",
                "Bilingual Marathi & English Question Toggle",
                "Anti-Screenshot Secure Offline Sync"
            ),
            isPopular = true
        ),
        SubscriptionPlan(
            id = "plan_lifetime",
            title = "Lifetime Rankers Club",
            price = 3499,
            billingCycle = "one-time payment",
            features = listOf(
                "Lifetime Access to All Upcoming Exam Test Series",
                "Direct Faculty Mentorship via Community",
                "Full Mains Answer Writing Masterclasses",
                "All Future State PCS & Central Govt Exam Packs"
            ),
            isPopular = false
        )
    )

    val examPapers: List<ExamPaper> = listOf(
        ExamPaper(
            id = "upsc_prelims_2024_gs1",
            title = "UPSC Civil Services Prelims 2024 GS-1 (Real Exam)",
            examCategory = ExamCategory.UPSC_PRELIMS,
            year = 2024,
            targetClass = "UPSC / Graduation",
            durationMinutes = 120,
            totalMarks = 200,
            negativeMarking = 0.66,
            questions = listOf(
                ExamQuestion(
                    id = "upsc_24_q1",
                    number = 1,
                    text = "With reference to the Constitution of India, in which of the following cases did the Supreme Court hold that the Right to Privacy is protected as an intrinsic part of the Right to Life and Personal Liberty under Article 21?",
                    textMarathi = "भारतीय संविधानाच्या संदर्भात, सर्वोच्च न्यायालयाने खालीलपैकी कोणत्या खटल्यात असा निकाल दिला की 'गोपनीयतेचा हक्क' (Right to Privacy) हा कलम २१ अंतर्गत जीवित आणि वैयक्तिक स्वातंत्र्याच्या हक्काचा अविभाज्य भाग आहे?",
                    options = listOf(
                        "A. Maneka Gandhi v. Union of India (1978)",
                        "B. Justice K.S. Puttaswamy (Retd.) v. Union of India (2017)",
                        "C. A.K. Gopalan v. State of Madras (1950)",
                        "D. Kesavananda Bharati v. State of Kerala (1973)"
                    ),
                    optionsMarathi = listOf(
                        "A. मनेका गांधी विरुद्ध भारत सरकार (१९७८)",
                        "B. न्यायमूर्ती के. एस. पुट्टास्वामी (निवृत्त) विरुद्ध भारत सरकार (२०१७)",
                        "C. ए. के. गोपालन विरुद्ध मद्रास राज्य (१९५०)",
                        "D. केशवानंद भारती विरुद्ध केरळ राज्य (१९७३)"
                    ),
                    correctOptionIndex = 1,
                    explanation = "In the landmark Justice K.S. Puttaswamy (Retd.) v. Union of India (2017) judgment, a nine-judge constitution bench of the Supreme Court unanimously affirmed that the Right to Privacy is a fundamental right under Article 21 and Part III of the Constitution.",
                    pyqYear = "UPSC CSE 2024",
                    subject = "Indian Polity"
                ),
                ExamQuestion(
                    id = "upsc_24_q2",
                    number = 2,
                    text = "Consider the following statements regarding the Monetary Policy Committee (MPC) of India:\n1. It is a 6-member committee constituted by the Central Government under the RBI Act, 1934.\n2. The Governor of the RBI serves as its ex-officio Chairperson.\n3. The MPC meets at least four times a year and each member has one vote, with the Governor having a casting vote.\nWhich of the statements given above are correct?",
                    textMarathi = "भारताच्या चलनविषयक धोरण समिती (MPC) बाबत खालील विधाने विचारात घ्या:\n१. ही आरबीआय कायदा, १९३४ अंतर्गत केंद्र सरकारने स्थापन केलेली ६ सदस्यीय समिती आहे.\n२. आरबीआयचे गव्हर्नर याचे पदसिद्ध अध्यक्ष असतात.\n३. MPC वर्षातून किमान चार वेळा भरते आणि प्रत्येक सदस्याला एक मत असते, तर बरोबरी झाल्यास गव्हर्नर निर्णायक मत देऊ शकतात.\nवरीलपैकी कोणती विधाने बरोबर आहेत?",
                    options = listOf(
                        "A. 1 and 2 only",
                        "B. 2 and 3 only",
                        "C. 1 and 3 only",
                        "D. 1, 2 and 3"
                    ),
                    optionsMarathi = listOf(
                        "A. फक्त १ आणि २",
                        "B. फक्त २ आणि ३",
                        "C. फक्त १ आणि ३",
                        "D. १, २ आणि ३ सर्व"
                    ),
                    correctOptionIndex = 3,
                    explanation = "All three statements are correct under Section 45ZB of the Reserve Bank of India Act, 1934. The MPC consists of 6 members (3 from RBI, 3 appointed by GoI). RBI Governor has a casting vote in case of a tie.",
                    pyqYear = "UPSC CSE 2024",
                    subject = "Indian Economy"
                ),
                ExamQuestion(
                    id = "upsc_24_q3",
                    number = 3,
                    text = "Which one of the following is the primary objective of the 'National Green Hydrogen Mission' launched by the Government of India?",
                    textMarathi = "भारत सरकारने सुरू केलेल्या 'राष्ट्रीय हरित हायड्रोजन अभियान' (National Green Hydrogen Mission) चे प्राथमिक उद्दिष्ट खालीलपैकी कोणते आहे?",
                    options = listOf(
                        "A. Complete electrification of Indian Railways by 2024",
                        "B. Producing at least 5 Million Metric Tonnes (MMT) of Green Hydrogen per annum by 2030",
                        "C. Replacing coal thermal power completely with nuclear plants by 2028",
                        "D. Subsidizing electric two-wheelers across rural agricultural clusters"
                    ),
                    optionsMarathi = listOf(
                        "A. २०२४ पर्यंत भारतीय रेल्वेचे संपूर्ण विद्युतीकरण करणे",
                        "B. २०३० पर्यंत प्रतिवर्ष किमान ५ दशलक्ष मेट्रिक टन (MMT) हरित हायड्रोजन उत्पादन क्षमता गाठणे",
                        "C. २०२८ पर्यंत कोळसा औष्णिक ऊर्जा प्रकल्प पूर्णपणे अणुप्रकल्पांनी बदलणे",
                        "D. ग्रामीण कृषी क्लस्टरमध्ये इलेक्ट्रिक दुचाकींवर अनुदान देणे"
                    ),
                    correctOptionIndex = 1,
                    explanation = "The National Green Hydrogen Mission aims to develop green hydrogen production capacity of at least 5 MMT per annum with an associated renewable energy capacity addition of about 125 GW in India by 2030.",
                    pyqYear = "UPSC CSE 2024",
                    subject = "Environment & Science"
                ),
                ExamQuestion(
                    id = "upsc_24_q4",
                    number = 4,
                    text = "With reference to ancient Indian history, the sites of Dholavira and Rakhigarhi belong to which period/civilization?",
                    textMarathi = "प्राचीन भारताच्या इतिहासाच्या संदर्भात, धोलाविरा आणि राखीगढी ही स्थळे कोणत्या कालखंडाशी/संस्कृतीशी संबंधित आहेत?",
                    options = listOf(
                        "A. Harappan (Indus Valley) Civilization",
                        "B. Megalithic Culture of South India",
                        "C. Painted Grey Ware (PGW) Vedic Culture",
                        "D. Early Mauryan Urban Clusters"
                    ),
                    optionsMarathi = listOf(
                        "A. हडप्पा (सिंधू संस्कृती)",
                        "B. दक्षिण भारतातील महापाषाण संस्कृती",
                        "C. चित्रित करड्या भांड्यांची वैदिक संस्कृती",
                        "D. सुरुवातीचे मौर्यकालीन नागरी क्लस्टर्स"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Dholavira (in Gujarat, famed for its sophisticated water management system and UNESCO World Heritage site) and Rakhigarhi (Haryana, largest Harappan site) belong to the Mature Harappan Civilization.",
                    pyqYear = "UPSC CSE 2024",
                    subject = "Ancient History"
                )
            )
        ),
        ExamPaper(
            id = "mpsc_rajyaseva_2024",
            title = "MPSC Rajyaseva Prelims 2024 (Paper 1 GS)",
            examCategory = ExamCategory.MPSC_RAJYASEVA,
            year = 2024,
            targetClass = "MPSC / Graduation",
            durationMinutes = 120,
            totalMarks = 200,
            negativeMarking = 0.50,
            questions = listOf(
                ExamQuestion(
                    id = "mpsc_24_q1",
                    number = 1,
                    text = "Mahatma Jyotirao Phule founded the 'Satyashodhak Samaj' on which date and where?",
                    textMarathi = "महात्मा जोतीराव फुले यांनी 'सत्यशोधक समाज' ची स्थापना कोणत्या दिवशी व कोठे केली?",
                    options = listOf(
                        "A. 24 September 1873 at Pune",
                        "B. 1 January 1848 at Mumbai",
                        "C. 15 August 1885 at Satara",
                        "D. 2 October 1869 at Kolhapur"
                    ),
                    optionsMarathi = listOf(
                        "A. २४ सप्टेंबर १८७३, पुणे येथे",
                        "B. १ जानेवारी १८४८, मुंबई येथे",
                        "C. १५ ऑगस्ट १८८५, सातारा येथे",
                        "D. २ ऑक्टोबर १८६९, कोल्हापूर येथे"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Mahatma Jyotirao Phule established the Satyashodhak Samaj in Pune on September 24, 1873, with the goal of liberating the oppressed classes and women through education and social equality.",
                    pyqYear = "MPSC 2024",
                    subject = "Maharashtra History"
                ),
                ExamQuestion(
                    id = "mpsc_24_q2",
                    number = 2,
                    text = "Which is the highest peak in the Sahyadri (Western Ghats) mountain range within Maharashtra?",
                    textMarathi = "महाराष्ट्रातील सह्याद्री पर्वतरांगेतील सर्वात उंच शिखर खालीलपैकी कोणते आहे?",
                    options = listOf(
                        "A. Salher (साल्हेर)",
                        "B. Kalsubai (कळसूबाई - १६४६ मीटर)",
                        "C. Mahabaleshwar (महाबळेश्वर)",
                        "D. Harishchandragad (हरिश्चंद्रगड)"
                    ),
                    optionsMarathi = listOf(
                        "A. साल्हेर (१५६७ मी)",
                        "B. कळसूबाई (१६४६ मी)",
                        "C. महाबळेश्वर (१४३८ मी)",
                        "D. हरिश्चंद्रगड (१४२४ मी)"
                    ),
                    correctOptionIndex = 1,
                    explanation = "Kalsubai peak, located in Ahmednagar district, is the highest peak in Maharashtra at an elevation of 1,646 meters (5,400 feet).",
                    pyqYear = "MPSC 2024",
                    subject = "Maharashtra Geography"
                ),
                ExamQuestion(
                    id = "mpsc_24_q3",
                    number = 3,
                    text = "Which committee was appointed in Maharashtra that recommended the three-tier Panchayati Raj system implemented on 1 May 1962?",
                    textMarathi = "महाराष्ट्रामध्ये त्रिस्तरीय पंचायत राज व्यवस्थेची शिफारस करण्यासाठी कोणती समिती नेमण्यात आली होती, जिच्या शिफारशीनुसार १ मे १९६२ रोजी पंचायत राज सुरू झाले?",
                    options = listOf(
                        "A. Balwantrai Mehta Committee",
                        "B. Vasantrao Naik Committee (वसंतराव नाईक समिती)",
                        "C. P.B. Patil Committee",
                        "D. L.N. Bongirwar Committee"
                    ),
                    optionsMarathi = listOf(
                        "A. बळवंतराय मेहता समिती",
                        "B. वसंतराव नाईक समिती",
                        "C. पी. बी. पाटील समिती",
                        "D. एल. एन. बोंगिरवार समिती"
                    ),
                    correctOptionIndex = 1,
                    explanation = "The Government of Maharashtra appointed the Vasantrao Naik Committee in 1960. Based on its recommendations, the Maharashtra Zilla Parishads and Panchayat Samitis Act, 1961 was passed, launching the system on 1 May 1962 with Zilla Parishad as the most powerful body.",
                    pyqYear = "MPSC 2024",
                    subject = "Maharashtra Polity & Governance"
                )
            )
        ),
        ExamPaper(
            id = "ssc_cgl_tier1_2024",
            title = "SSC CGL 2024 Tier 1 (GS & Quantitative)",
            examCategory = ExamCategory.SSC_CGL,
            year = 2024,
            targetClass = "Graduate / Central Govt",
            durationMinutes = 60,
            totalMarks = 200,
            negativeMarking = 0.50,
            questions = listOf(
                ExamQuestion(
                    id = "ssc_24_q1",
                    number = 1,
                    text = "Under Article 148 of the Indian Constitution, who is appointed as the guardian of the public purse and head of the Indian Audit and Accounts Department?",
                    options = listOf(
                        "A. Attorney General of India",
                        "B. Comptroller and Auditor General of India (CAG)",
                        "C. Finance Minister of India",
                        "D. Chairperson of Finance Commission"
                    ),
                    correctOptionIndex = 1,
                    explanation = "The Comptroller and Auditor General (CAG) is established under Article 148. Dr. B.R. Ambedkar regarded CAG as the most important office under the Constitution of India.",
                    pyqYear = "SSC CGL 2024",
                    subject = "Polity"
                ),
                ExamQuestion(
                    id = "ssc_24_q2",
                    number = 2,
                    text = "The Tropic of Cancer does NOT pass through which of the following Indian states?",
                    options = listOf(
                        "A. Rajasthan",
                        "B. Chhattisgarh",
                        "C. Odisha",
                        "D. Tripura"
                    ),
                    correctOptionIndex = 2,
                    explanation = "The Tropic of Cancer (23.5° N) passes through 8 states: Gujarat, Rajasthan, Madhya Pradesh, Chhattisgarh, Jharkhand, West Bengal, Tripura, and Mizoram. It does NOT pass through Odisha.",
                    pyqYear = "SSC CGL 2024",
                    subject = "Geography"
                )
            )
        ),
        ExamPaper(
            id = "group_c_talathi_2024",
            title = "Maharashtra Group C Talathi & Clerk Combined (TCS/IBPS)",
            examCategory = ExamCategory.GROUP_C_D,
            year = 2024,
            targetClass = "12th / Graduate",
            durationMinutes = 120,
            totalMarks = 200,
            negativeMarking = 0.25,
            questions = listOf(
                ExamQuestion(
                    id = "talathi_24_q1",
                    number = 1,
                    text = "In Marathi Grammar, what is the Sandhi of 'सत् + जन'?",
                    textMarathi = "मराठी व्याकरणात 'सत् + जन' या विग्रहाची योग्य संधी खालीलपैकी कोणती?",
                    options = listOf(
                        "A. सतजन",
                        "B. सज्जन",
                        "C. साजन",
                        "D. सदजन"
                    ),
                    optionsMarathi = listOf(
                        "A. सतजन",
                        "B. सज्जन",
                        "C. साजन",
                        "D. सदजन"
                    ),
                    correctOptionIndex = 1,
                    explanation = "'त्' चा नियम: त् पुढे 'ज' किंवा 'झ' आल्यास 'त्' बद्दल 'ज्' होतो. उदा. सत् + जन = सज्जन (व्यंजन संधी).",
                    pyqYear = "Talathi Bharti 2024",
                    subject = "मराठी व्याकरण"
                ),
                ExamQuestion(
                    id = "talathi_24_q2",
                    number = 2,
                    text = "Maharashtra state was officially formed on which date?",
                    textMarathi = "महाराष्ट्र राज्याची अधिकृत स्थापना कोणत्या दिवशी झाली?",
                    options = listOf(
                        "A. 15 August 1947",
                        "B. 26 January 1950",
                        "C. 1 May 1960",
                        "D. 1 November 1956"
                    ),
                    optionsMarathi = listOf(
                        "A. १५ ऑगस्ट १९४७",
                        "B. २६ जानेवारी १९५०",
                        "C. १ मे १९६०",
                        "D. १ नोव्हेंबर १९५६"
                    ),
                    correctOptionIndex = 2,
                    explanation = "Through the Bombay Reorganisation Act, Maharashtra was created on 1 May 1960 with Mumbai as its capital.",
                    pyqYear = "Police Bharti / Group C",
                    subject = "सामान्य ज्ञान"
                )
            )
        )
    )

    val gamifiedUnits: List<GamifiedLessonUnit> = listOf(
        GamifiedLessonUnit(
            id = "unit_1_polity",
            unitNumber = 1,
            title = "Constitution & Polity Blitz",
            topic = "Fundamental Rights, DPSP & Parliament Speed Run",
            stages = listOf(
                GamifiedStage(
                    id = "stage_u1_s1",
                    stageNumber = 1,
                    title = "Preamble & Fundamental Rights",
                    xpReward = 30,
                    isUnlocked = true,
                    isCompleted = true,
                    stars = 3,
                    questions = listOf(
                        GamifiedQuestion(
                            id = "gq_1",
                            type = QuestionType.MCQ,
                            prompt = "Which Article of the Constitution of India abolishes 'Untouchability'?",
                            options = listOf("Article 14", "Article 17", "Article 19", "Article 21"),
                            correctOptionIndex = 1,
                            explanation = "Article 17 explicitly abolishes untouchability and forbids its practice in any form.",
                            mnemonic = "Memory Tip: Article 17 = 17 Khatra for untouchability!"
                        ),
                        GamifiedQuestion(
                            id = "gq_2",
                            type = QuestionType.TRUE_FALSE,
                            prompt = "True or False: The Preamble is an integral part of the Indian Constitution as ruled in the Kesavananda Bharati case.",
                            options = listOf("True", "False"),
                            correctOptionIndex = 0,
                            explanation = "Yes, the Supreme Court ruled in 1973 that the Preamble is part of the Constitution and can be amended under Article 368 without violating the basic structure."
                        ),
                        GamifiedQuestion(
                            id = "gq_3",
                            type = QuestionType.MCQ,
                            prompt = "How many Fundamental Freedoms are guaranteed under Article 19?",
                            options = listOf("5 Freedoms", "6 Freedoms", "7 Freedoms", "8 Freedoms"),
                            correctOptionIndex = 1,
                            explanation = "Article 19 guarantees 6 freedoms (originally 7, but right to acquire property was removed by the 44th Amendment in 1978)."
                        )
                    )
                ),
                GamifiedStage(
                    id = "stage_u1_s2",
                    stageNumber = 2,
                    title = "Directive Principles & Fundamental Duties",
                    xpReward = 35,
                    isUnlocked = true,
                    isCompleted = true,
                    stars = 2,
                    questions = listOf(
                        GamifiedQuestion(
                            id = "gq_4",
                            type = QuestionType.MCQ,
                            prompt = "Fundamental Duties were added to the Constitution on the recommendation of which Committee?",
                            options = listOf("Sarkaria Commission", "Swaran Singh Committee", "Verma Committee", "Punchhi Commission"),
                            correctOptionIndex = 1,
                            explanation = "The Swaran Singh Committee recommended Fundamental Duties, added by the 42nd Amendment Act, 1976 (Part IV-A, Article 51A)."
                        ),
                        GamifiedQuestion(
                            id = "gq_5",
                            type = QuestionType.MCQ,
                            prompt = "Uniform Civil Code (UCC) is mentioned in which Article of DPSP?",
                            options = listOf("Article 40", "Article 44", "Article 48", "Article 50"),
                            correctOptionIndex = 1,
                            explanation = "Article 44 urges the State to secure for citizens a Uniform Civil Code throughout the territory of India."
                        )
                    )
                ),
                GamifiedStage(
                    id = "stage_u1_s3",
                    stageNumber = 3,
                    title = "President & Governor Powers",
                    xpReward = 40,
                    isUnlocked = true,
                    isCompleted = false,
                    stars = 0,
                    questions = listOf(
                        GamifiedQuestion(
                            id = "gq_6",
                            type = QuestionType.MCQ,
                            prompt = "Which Article empowers the President of India to promulgate Ordinances during recess of Parliament?",
                            options = listOf("Article 110", "Article 123", "Article 213", "Article 356"),
                            correctOptionIndex = 1,
                            explanation = "Article 123 grants ordinance-making power to the President, while Article 213 grants it to the Governor."
                        )
                    )
                ),
                GamifiedStage(
                    id = "stage_u1_s4",
                    stageNumber = 4,
                    title = "Supreme Court & Judicial Review",
                    xpReward = 45,
                    isUnlocked = false,
                    isCompleted = false,
                    stars = 0
                ),
                GamifiedStage(
                    id = "stage_u1_s5",
                    stageNumber = 5,
                    title = "Panchayati Raj & 73rd Amendment Challenge",
                    xpReward = 50,
                    isUnlocked = false,
                    isCompleted = false,
                    stars = 0
                )
            )
        ),
        GamifiedLessonUnit(
            id = "unit_2_maharashtra",
            unitNumber = 2,
            title = "Maharashtra Darshan & History",
            topic = "Chhatrapati Shivaji Maharaj, Social Reformers & Geography",
            stages = listOf(
                GamifiedStage(
                    id = "stage_u2_s1",
                    stageNumber = 1,
                    title = "Forts & Maratha Administration",
                    xpReward = 30,
                    isUnlocked = true,
                    isCompleted = false,
                    stars = 0,
                    questions = listOf(
                        GamifiedQuestion(
                            id = "gq_mh1",
                            type = QuestionType.MCQ,
                            prompt = "At which fort was the coronation (Rajyabhishek) of Chhatrapati Shivaji Maharaj performed on 6 June 1674?",
                            options = listOf("Raigad (रायगड)", "Shivneri (शिवनेरी)", "Pratapgad (प्रतापगड)", "Sinhagad (सिंहगड)"),
                            correctOptionIndex = 0,
                            explanation = "Chhatrapati Shivaji Maharaj was crowned Chhatrapati at Fort Raigad on 6 June 1674 by Gaga Bhatt."
                        ),
                        GamifiedQuestion(
                            id = "gq_mh2",
                            type = QuestionType.MCQ,
                            prompt = "The council of eight ministers in Chhatrapati Shivaji Maharaj's administration was known as:",
                            options = listOf("Navratna", "Ashtapradhan Mandal (अष्टप्रधान मंडळ)", "Ashtadiggajas", "Panchayat"),
                            correctOptionIndex = 1,
                            explanation = "The Ashtapradhan Mandal was an administrative advisory council of eight ministers with the Peshwa at its head."
                        )
                    )
                ),
                GamifiedStage(
                    id = "stage_u2_s2",
                    stageNumber = 2,
                    title = "Maharashtra Rivers & Ghats",
                    xpReward = 35,
                    isUnlocked = false,
                    isCompleted = false,
                    stars = 0
                )
            )
        ),
        GamifiedLessonUnit(
            id = "unit_3_school_foundation",
            unitNumber = 3,
            title = "School Foundation (Class 5th - 12th)",
            topic = "NCERT Core Science, Environmental Studies & World Geography",
            stages = listOf(
                GamifiedStage(
                    id = "stage_u3_s1",
                    stageNumber = 1,
                    title = "Earth, Atmosphere & Solar System (Class 5-6)",
                    xpReward = 25,
                    isUnlocked = true,
                    isCompleted = false,
                    stars = 0,
                    questions = listOf(
                        GamifiedQuestion(
                            id = "gq_sc1",
                            type = QuestionType.MCQ,
                            prompt = "Which layer of the atmosphere contains the protective Ozone layer that absorbs harmful Ultraviolet (UV) rays?",
                            options = listOf("Troposphere", "Stratosphere", "Mesosphere", "Thermosphere"),
                            correctOptionIndex = 1,
                            explanation = "The Stratosphere (between approx 15 to 50 km) houses the ozone layer."
                        )
                    )
                )
            )
        )
    )

    val schoolGrades: List<SchoolGradeItem> = listOf(
        SchoolGradeItem(
            gradeName = "Class 5th",
            displayName = "Class 5th (Junior Foundation)",
            keySubjects = listOf("EVS (Environment)", "Basic Mathematics", "General Knowledge", "Language Skills"),
            totalLessons = 32,
            foundationFocus = "Observation skills, wildlife conservation, state symbols, and natural phenomena."
        ),
        SchoolGradeItem(
            gradeName = "Class 6th",
            displayName = "Class 6th (NCERT Explorer)",
            keySubjects = listOf("Early Civilizations (Indus)", "Earth & Solar System", "Fractions & Decimals", "Living Organisms"),
            totalLessons = 40,
            foundationFocus = "Introduction to history timelines, geographical coordinates, and plant/animal habitats."
        ),
        SchoolGradeItem(
            gradeName = "Class 7th",
            displayName = "Class 7th (Medieval & Environment)",
            keySubjects = listOf("Medieval Kingdoms", "Weather & Climate", "Nutrition in Plants", "Panchayat & Democracy"),
            totalLessons = 45,
            foundationFocus = "Maratha Empire, Delhi Sultanate, water cycle, and constitutional equality."
        ),
        SchoolGradeItem(
            gradeName = "Class 8th",
            displayName = "Class 8th (Modern India & Science)",
            keySubjects = listOf("1857 Revolt", "Indian Constitution", "Cell Structure & Microorganisms", "Resource Conservation"),
            totalLessons = 50,
            foundationFocus = "Colonial rule impact, fundamental rights, crop production, and force & pressure."
        ),
        SchoolGradeItem(
            gradeName = "Class 9th",
            displayName = "Class 9th (Civil Services Pre-Cadet)",
            keySubjects = listOf("Democratic Politics", "Indian Economy & Poverty", "Physical Features of India", "Motion & Gravity"),
            totalLessons = 60,
            foundationFocus = "Electoral politics, food security, Himalayan & Peninsular river basins."
        ),
        SchoolGradeItem(
            gradeName = "Class 10th",
            displayName = "Class 10th (SSC Board & IAS Prep)",
            keySubjects = listOf("Nationalism in India", "Resources & Agriculture", "Federalism & Gender", "Electricity & Light"),
            totalLessons = 75,
            foundationFocus = "Core foundation for UPSC Prelims modern history, geography maps, and basic economics."
        ),
        SchoolGradeItem(
            gradeName = "Class 11th",
            displayName = "Class 11th (Advanced NCERT Foundation)",
            keySubjects = listOf("Physical Geography", "Indian Constitution at Work", "Economic Development", "Sociology Basics"),
            totalLessons = 90,
            foundationFocus = "Geomorphology, climatology, oceanography, fundamental rights analysis, and pre-1991 reforms."
        ),
        SchoolGradeItem(
            gradeName = "Class 12th",
            displayName = "Class 12th (HSC Board & Civil Services)",
            keySubjects = listOf("Themes in Indian History", "Macroeconomics & Budget", "Contemporary World Politics", "Human Geography"),
            totalLessons = 100,
            foundationFocus = "National income accounting, monetary policy, Harappa to Partition in-depth, and international relations."
        ),
        SchoolGradeItem(
            gradeName = "Graduation",
            displayName = "Undergrad / Graduation (Direct Officer Track)",
            keySubjects = listOf("UPSC GS Papers 1 to 4", "MPSC Rajyaseva / Combine", "CSAT Aptitude", "Current Affairs & Editorial Analysis"),
            totalLessons = 250,
            foundationFocus = "Intensive PYQs, timed mock tests, essay perspectives, ethics case studies, and interview guidance."
        )
    )
}
