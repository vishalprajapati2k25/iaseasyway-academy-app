package com.iaseasyway.academy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iaseasyway.academy.data.repository.AcademyRepository
import com.iaseasyway.academy.ui.theme.*

@Composable
fun ProfileScreen(
    repository: AcademyRepository,
    modifier: Modifier = Modifier
) {
    val userProfile by repository.userProfile.collectAsState()
    val courses by repository.courses.collectAsState()
    val liveArticles by repository.liveArticles.collectAsState()

    val subscribedList = courses.filter { it.isSubscribed }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate100),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Profile Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Navy900,
                elevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(DuolingoGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "👑", fontSize = 36.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = userProfile.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = PureWhite
                    )

                    Text(
                        text = userProfile.email,
                        fontSize = 12.sp,
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Stats Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Navy800)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "🔥 ${userProfile.streakDays}", fontSize = 16.sp, fontWeight = FontWeight.Black, color = DuolingoGold)
                            Text(text = "Day Streak", fontSize = 10.sp, color = TextMuted)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "⚡ ${userProfile.totalXp}", fontSize = 16.sp, fontWeight = FontWeight.Black, color = DuolingoBlue)
                            Text(text = "Total XP", fontSize = 10.sp, color = TextMuted)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "❤️ ${userProfile.hearts}/${userProfile.maxHearts}", fontSize = 16.sp, fontWeight = FontWeight.Black, color = DuolingoRed)
                            Text(text = "Hearts/Lives", fontSize = 10.sp, color = TextMuted)
                        }
                    }

                    if (userProfile.hearts < userProfile.maxHearts) {
                        Spacer(modifier = Modifier.height(10.dp))
                        TextButton(onClick = { repository.refillHearts() }) {
                            Text("Refill Hearts to 5 ❤️", color = DuolingoGold, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Security & Anti-Theft Protection Status
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = PureWhite,
                elevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "Security",
                            tint = DuolingoGreen,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Proprietary Content Security & DRM",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextDark
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    SecurityFeatureItem(
                        title = "Anti-Screenshot Protection (FLAG_SECURE)",
                        desc = "Active. WindowManager hardware flag blocks screenshots, screen recording, and system window caching."
                    )
                    SecurityFeatureItem(
                        title = "Dynamic API Question Ingestion",
                        desc = "Active. Questions are decrypted and retrieved from API endpoints, never stored as plain assets."
                    )
                    SecurityFeatureItem(
                        title = "R8 Obfuscation & Anti-Recompilation",
                        desc = "Active. Package flattening, debug log stripping, and dead-code elimination prevent reverse engineering."
                    )
                }
            }
        }

        // Subscribed Courses
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = PureWhite,
                elevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "My Subscribed Courses (${subscribedList.size})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = TextDark
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    if (subscribedList.isEmpty()) {
                        Text(
                            text = "No active course subscription yet. Visit Courses tab to subscribe.",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    } else {
                        subscribedList.forEach { course ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Slate100)
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = course.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = TextDark
                                    )
                                    Text(
                                        text = "Instructor: ${course.instructor}",
                                        fontSize = 11.sp,
                                        color = TextMuted
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(DuolingoGreen)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("ACTIVE", color = PureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Live Website Feed (iaseasyway.com)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = PureWhite,
                elevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Live Notes from iaseasyway.com",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextDark
                        )
                        IconButton(onClick = { repository.refreshLiveFeed() }) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh", tint = Navy700)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    if (liveArticles.isEmpty()) {
                        Text(
                            text = "Fetching latest articles and current affairs from iaseasyway.com WordPress API...",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    } else {
                        liveArticles.take(4).forEach { post ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                            ) {
                                Text(
                                    text = post.title,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp,
                                    color = Navy700
                                )
                                if (post.excerpt.isNotBlank()) {
                                    Text(
                                        text = post.excerpt.take(100) + "...",
                                        fontSize = 11.sp,
                                        color = TextMuted,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                                Divider(modifier = Modifier.padding(top = 8.dp), color = Slate200)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SecurityFeatureItem(title: String, desc: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = DuolingoGreen,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
        }
        Text(
            text = desc,
            fontSize = 11.sp,
            color = TextMuted,
            modifier = Modifier.padding(start = 20.dp, top = 2.dp)
        )
    }
}
