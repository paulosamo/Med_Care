package com.example.medcare.HospitalDirectory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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

// ===================== DATA =====================

data class Doctor(
    val name: String,
    val specialty: String,
    val experience: String,
    val rating: Float,
    val reviews: Int,
    val hospital: String,
    val availability: String,
    val bio: String
)

fun doctors() = listOf(
    Doctor("Dr. Sarah Chen", "Cardiologist", "12 yrs", 4.8f, 245, "KNH", "Tomorrow", "Heart specialist"),
    Doctor("Dr. James Mwangi", "General Physician", "8 yrs", 4.6f, 189, "Nairobi Hospital", "Today", "General care"),
    Doctor("Dr. Aisha Khan", "Pediatrician", "10 yrs", 4.9f, 312, "KNH", "Friday", "Child health expert")
)

// ===================== AI RECOMMENDER =====================

fun recommendDoctors(query: String, list: List<Doctor>): List<Doctor> {
    if (query.isBlank()) return list
    return list.sortedByDescending {
        if (it.specialty.contains(query, true)) 2 else if (it.name.contains(query, true)) 1 else 0
    }
}

// ===================== MAIN SCREEN =====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorsScreen(navController: NavHostController? = null) {

    var search by remember { mutableStateOf("") }
    var selectedDoctor by remember { mutableStateOf<Doctor?>(null) }
    var selectedDate by remember { mutableStateOf("Select Date") }
    var review by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(4f) }

    val list = recommendDoctors(search, doctors())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Find Doctors", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            // 🔍 SEARCH + AI
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Search, null) },
                label = { Text("Search doctor or specialty") }
            )

            Spacer(Modifier.height(12.dp))

            Text(
                "🤖 AI Recommended Doctors",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(8.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                items(list) { d ->
                    DoctorCard(d) { selectedDoctor = d }
                }

                item { Spacer(Modifier.height(100.dp)) }
            }
        }
    }

    // ===================== BOOKING SHEET =====================

    if (selectedDoctor != null) {

        ModalBottomSheet(
            onDismissRequest = { selectedDoctor = null }
        ) {

            Column(Modifier.padding(20.dp)) {

                Text(selectedDoctor!!.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(selectedDoctor!!.specialty, color = MaterialTheme.colorScheme.primary)

                Spacer(Modifier.height(10.dp))

                // 📅 CALENDAR PICKER (SIMPLIFIED UI)
                OutlinedButton(
                    onClick = {
                        selectedDate = "22 May 2026 (Selected)"
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedDate)
                }

                Spacer(Modifier.height(10.dp))

                // ⭐ REVIEW SYSTEM
                Text("Rate Doctor: ${rating.toInt()} ⭐")
                Slider(
                    value = rating,
                    onValueChange = { rating = it },
                    valueRange = 1f..5f
                )

                OutlinedTextField(
                    value = review,
                    onValueChange = { review = it },
                    label = { Text("Write Review") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController?.navigate("appointment_success")
                        selectedDoctor = null
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Book Appointment")
                }

                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

// ===================== DOCTOR CARD =====================

@Composable
fun DoctorCard(doctor: Doctor, onClick: () -> Unit) {

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(20.dp)
    ) {

        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text("👨‍⚕️", fontSize = 28.sp)
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(doctor.name, fontWeight = FontWeight.Bold)
                Text(doctor.specialty, color = MaterialTheme.colorScheme.primary)
                Text("${doctor.experience} • ${doctor.hospital}", fontSize = 12.sp)
                Text("⭐ ${doctor.rating} (${doctor.reviews})", fontSize = 12.sp)
            }

            Text(
                doctor.availability,
                color = Color.Green,
                fontSize = 12.sp
            )
        }
    }
}

// ===================== PREVIEW =====================

@Preview(showBackground = true)
@Composable
fun DoctorPreview() {
    MaterialTheme {
        DoctorsScreen()
    }
}