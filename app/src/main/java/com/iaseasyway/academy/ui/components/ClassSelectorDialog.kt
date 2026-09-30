package com.iaseasyway.academy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.iaseasyway.academy.ui.theme.*

@Composable
fun ClassSelectorDialog(
    currentGrade: String,
    onGradeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val options = listOf(
        "Class 5th (Junior Foundation)",
        "Class 6th (NCERT Explorer)",
        "Class 7th (Medieval & Environment)",
        "Class 8th (Modern India & Science)",
        "Class 9th (Civil Services Pre-Cadet)",
        "Class 10th (SSC Board & IAS Prep)",
        "Class 11th (Advanced NCERT Foundation)",
        "Class 12th (HSC Board & Civil Services)",
        "Undergrad / Graduation (Direct Track)",
        "UPSC Civil Services (IAS/IPS)",
        "MPSC Rajyaseva & Combined (MH)",
        "SSC CGL / CHSL (Central Govt)",
        "Maharashtra Group C & D (Talathi/Police)"
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Navy900,
            elevation = 16.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Select Class or Target Exam",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PureWhite
                        )
                        Text(
                            text = "Personalize your learning & questions",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = PureWhite
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(options) { opt ->
                        val isSelected = currentGrade.contains(opt.take(9)) || currentGrade == opt
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) Navy700 else Navy800)
                                .clickable {
                                    onGradeSelected(opt)
                                    onDismiss()
                                }
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = opt,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) DuolingoGold else PureWhite
                            )
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = DuolingoGreen
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
