package com.iaseasyway.academy.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.iaseasyway.academy.data.repository.AcademyRepository
import com.iaseasyway.academy.data.seed.SeedData
import com.iaseasyway.academy.model.Course
import com.iaseasyway.academy.model.SubscriptionPlan
import com.iaseasyway.academy.ui.theme.*

@Composable
fun CoursesCatalogScreen(
    repository: AcademyRepository,
    modifier: Modifier = Modifier
) {
    val courses by repository.courses.collectAsState()
    var selectedCourseForSubscription by remember { mutableStateOf<Course?>(null) }
    var showSubscriptionPlansDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Slate100)
    ) {
        // Academy Header
        Surface(
            color = Navy900,
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "IEW Academy Courses",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "Expert Mentorship & High-Yield Civil Services Batches",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }

                    Button(
                        onClick = { showSubscriptionPlansDialog = true },
                        colors = ButtonDefaults.buttonColors(backgroundColor = DuolingoGold),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "PASS TIERS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Navy900
                        )
                    }
                }
            }
        }

        // Courses List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(courses) { course ->
                CourseCard(
                    course = course,
                    onSubscribeClick = {
                        selectedCourseForSubscription = course
                    }
                )
            }
        }

        // Subscription Plans Dialog
        if (showSubscriptionPlansDialog) {
            SubscriptionTiersDialog(
                onDismiss = { showSubscriptionPlansDialog = false },
                onSelectPlan = { plan ->
                    showSubscriptionPlansDialog = false
                    // Subscribe to top course as unlock demonstration
                    repository.subscribeCourse("course_mpsc_rajyaseva_2025")
                }
            )
        }

        // Single Course Subscription Flow Dialog
        selectedCourseForSubscription?.let { course ->
            CourseDetailModal(
                course = course,
                onDismiss = { selectedCourseForSubscription = null },
                onConfirmEnroll = {
                    repository.subscribeCourse(course.id)
                    selectedCourseForSubscription = null
                }
            )
        }
    }
}

@Composable
private fun CourseCard(
    course: Course,
    onSubscribeClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = PureWhite,
        elevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (course.isSubscribed) DuolingoGreen else DuolingoGold)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = if (course.isSubscribed) "ENROLLED ✓" else course.badgeText.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = if (course.isSubscribed) PureWhite else Navy900
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = DuolingoGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${course.rating}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = TextDark
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "(${course.totalStudents})",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = course.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextDark
            )

            Text(
                text = course.subtitle,
                fontSize = 12.sp,
                color = TextMuted,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Faculty: ${course.instructor}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Navy700
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "₹${course.discountPrice}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = DuolingoGreenDark
                    )
                    Text(
                        text = "Regular ₹${course.price}",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }

                Button(
                    onClick = onSubscribeClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (course.isSubscribed) Navy700 else DuolingoGreen
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.height(42.dp)
                ) {
                    Text(
                        text = if (course.isSubscribed) "VIEW SYLLABUS" else "SUBSCRIBE NOW",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                }
            }
        }
    }
}

@Composable
fun CourseDetailModal(
    course: Course,
    onDismiss: () -> Unit,
    onConfirmEnroll: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = PureWhite,
            elevation = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = course.category,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy700
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Text(
                    text = course.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Included Modules & Tests:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )

                Spacer(modifier = Modifier.height(6.dp))

                course.syllabusModules.forEach { mod ->
                    Row(
                        modifier = Modifier.padding(vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = DuolingoGreen,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = mod,
                            fontSize = 12.sp,
                            color = TextDark
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onConfirmEnroll,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (course.isSubscribed) Navy700 else DuolingoGreen
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                ) {
                    Text(
                        text = if (course.isSubscribed) "ALREADY ENROLLED (ACCESS LESSONS)" else "CONFIRM SUBSCRIPTION (₹${course.discountPrice})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = PureWhite
                    )
                }
            }
        }
    }
}

@Composable
fun SubscriptionTiersDialog(
    onDismiss: () -> Unit,
    onSelectPlan: (SubscriptionPlan) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Navy900,
            elevation = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "IEW Academy Passes",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = PureWhite)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                SeedData.subscriptionPlans.forEach { plan ->
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        backgroundColor = if (plan.isPopular) Navy700 else Navy800,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { onSelectPlan(plan) }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = plan.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (plan.isPopular) DuolingoGold else PureWhite
                                )
                                Text(
                                    text = "₹${plan.price} ${plan.billingCycle}",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp,
                                    color = DuolingoGreen
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            plan.features.take(2).forEach { feat ->
                                Text(
                                    text = "• $feat",
                                    fontSize = 11.sp,
                                    color = TextMuted
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
