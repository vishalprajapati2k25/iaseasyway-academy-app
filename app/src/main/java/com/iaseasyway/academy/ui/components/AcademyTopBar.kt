package com.iaseasyway.academy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iaseasyway.academy.model.UserProfile
import com.iaseasyway.academy.ui.theme.*

@Composable
fun AcademyTopBar(
    userProfile: UserProfile,
    onClassClick: () -> Unit,
    onRefillHeartsClick: () -> Unit = {}
) {
    Surface(
        color = Navy900,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand Title
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(DuolingoGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "IEW",
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            color = PureWhite
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "IEW Academy",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "IAS EasyWay Platform",
                            fontSize = 10.sp,
                            color = DuolingoGold
                        )
                    }
                }

                // Gamification Stats Header (Streak, Hearts, XP)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Streak Pill
                    StatPill(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = "Streak",
                                tint = DuolingoGold,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        text = "${userProfile.streakDays}",
                        textColor = DuolingoGold
                    )

                    // Hearts Pill
                    StatPill(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Hearts",
                                tint = DuolingoRed,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        text = "${userProfile.hearts}",
                        textColor = DuolingoRed,
                        onClick = onRefillHeartsClick
                    )

                    // XP Pill
                    StatPill(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.ElectricBolt,
                                contentDescription = "XP",
                                tint = DuolingoBlue,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        text = "${userProfile.totalXp}",
                        textColor = DuolingoBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Class Selection Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(Navy800)
                    .clickable { onClassClick() }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "Target",
                        tint = DuolingoGreenLight,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Current Track: ${userProfile.currentGrade}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = PureWhite
                    )
                }
                Text(
                    text = "Change ▼",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DuolingoGold
                )
            }
        }
    }
}

@Composable
private fun StatPill(
    icon: @Composable () -> Unit,
    text: String,
    textColor: Color,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Navy800)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
