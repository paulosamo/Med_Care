
package com.example.medcare.HealthProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class PersonalInfo(
    val name: String,
    val age: String,
    val blood: String,
    val phone: String,
    val emergency: String
)

data class LabReport(
    val title: String,
    val date: String,
    val result: String
)

data class Vaccine(
    val name: String,
    val date: String
)

data class Visit(
    val doctor: String,
    val reason: String,
    val date: String
)

val personalInfo = PersonalInfo(
    "Paul Osamo",
    "24",
    "O+",
    "+254 700 123 456",
    "Ester Osamo"
)

val reports = listOf(
    LabReport("Blood Test", "15 May 2026", "Normal"),
    LabReport("Blood Sugar", "10 May 2026", "Good")
)

val vaccines = listOf(
    Vaccine("COVID-19", "10 Jan 2026"),
    Vaccine("Tetanus", "12 Dec 2025")
)

val visits = listOf(
    Visit("Dr. Innocent Mose", "Routine Checkup", "12 May 2026"),
    Visit("Dr. James Mwangi", "Headache", "5 April 2026")
)

// ===================== MAIN SCREEN =====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthProfileScreen(
    navController: NavHostController? = null
) {

    var showDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text(
                        "Health Profile",
                        fontWeight = FontWeight.Bold
                    )
                },

                actions = {

                    IconButton(
                        onClick = { showDialog = true }
                    ) {

                        Icon(Icons.Default.Edit, null)
                    }
                }
            )
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {

                Spacer(modifier = Modifier.height(8.dp))

                // PROFILE CARD
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                Icons.Default.Person,
                                null,
                                modifier = Modifier.size(50.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            personalInfo.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            "Patient Profile",
                            color = Color.Gray
                        )
                    }
                }
            }

            // PERSONAL INFO
            item {

                SectionTitle("Personal Information")

                InfoCard(
                    Icons.Default.Bloodtype,
                    "Blood Group",
                    personalInfo.blood
                )

                InfoCard(
                    Icons.Default.Call,
                    "Phone",
                    personalInfo.phone
                )

                InfoCard(
                    Icons.Default.LocalHospital,
                    "Emergency Contact",
                    personalInfo.emergency
                )
            }

            // LAB REPORTS
            item {

                SectionTitle("Lab Reports")

                reports.forEach { report ->

                    SimpleCard(
                        icon = Icons.Default.Description,
                        title = report.title,
                        subtitle = "${report.date} • ${report.result}"
                    )
                }
            }

            // VACCINES
            item {

                SectionTitle("Vaccinations")

                vaccines.forEach { vaccine ->

                    SimpleCard(
                        icon = Icons.Default.Vaccines,
                        title = vaccine.name,
                        subtitle = vaccine.date
                    )
                }
            }

            // VISITS
            item {

                SectionTitle("Visit History")

                visits.forEach { visit ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),

                        shape = RoundedCornerShape(18.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                visit.doctor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(visit.reason)

                            Text(
                                visit.date,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }

    // EDIT DIALOG
    if (showDialog) {

        AlertDialog(

            onDismissRequest = {
                showDialog = false
            },

            title = {
                Text("Edit Profile")
            },

            text = {
                Text("Profile editing feature coming soon.")
            },

            confirmButton = {

                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}

// ===================== COMPONENTS =====================

@Composable
fun SectionTitle(title: String) {

    Text(
        title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 10.dp)
    )
}

@Composable
fun InfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),

        shape = RoundedCornerShape(18.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                icon,
                null,
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column {

                Text(
                    title,
                    color = Color.Gray
                )

                Text(
                    value,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SimpleCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),

        shape = RoundedCornerShape(18.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                icon,
                null,
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column {

                Text(
                    title,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    subtitle,
                    color = Color.Gray
                )
            }
        }
    }
}

// ===================== PREVIEW =====================

@Preview(showBackground = true)
@Composable
fun HealthProfilePreview() {

    MaterialTheme {

        HealthProfileScreen()
    }
}