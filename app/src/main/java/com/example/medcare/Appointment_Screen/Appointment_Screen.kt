package com.example.medcare.Appointment_Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
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

// ===================== DATA MODEL =====================

data class Appointment(
    val doctor: String,
    val specialty: String,
    val date: String,
    val time: String,
    val hospital: String,
    val status: String
)

val upcomingAppointments = listOf(
    Appointment(
        "Dr. Innocent Mose",
        "Wound Specialist",
        "Tomorrow",
        "10:30 AM",
        "The Nairobi Hospital",
        "Confirmed"
    ),
    Appointment(
        "Dr. James Mwangi",
        "General Physician",
        "28 May 2026",
        "2:00 PM",
        "Nairobi Hospital",
        "Confirmed"
    )
)

val pastAppointments = listOf(
    Appointment(
        "Dr. Aisha Khan",
        "Pediatrician",
        "10 May 2026",
        "11:00 AM",
        "Kenyatta National Hospital",
        "Completed"
    )
)

// ===================== MAIN SCREEN =====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentScreen(navController: NavHostController? = null) {

    var selectedFilter by remember { mutableStateOf("All") }
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Appointments",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBottomSheet = true }
            ) {
                Icon(Icons.Default.Add, null)
            }
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

                Spacer(modifier = Modifier.height(10.dp))

                // HEADER CARD
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        Text(
                            "You have 2 upcoming appointments",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Stay updated with your health schedule.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // FILTERS
            item {

                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    listOf("Today", "This Week", "All").forEach { filter ->

                        FilterChip(
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter },
                            label = { Text(filter) }
                        )
                    }
                }
            }

            // UPCOMING
            item {
                Text(
                    "Upcoming Appointments",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(upcomingAppointments) { appointment ->
                AppointmentCard(appointment, true)
            }

            // PAST
            item {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "Past Appointments",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(pastAppointments) { appointment ->
                AppointmentCard(appointment, false)
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }

    // BOTTOM SHEET
    if (showBottomSheet) {

        BookAppointmentSheet(
            onDismiss = { showBottomSheet = false }
        )
    }
}

// ===================== APPOINTMENT CARD =====================

@Composable
fun AppointmentCard(
    appointment: Appointment,
    upcoming: Boolean
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        appointment.doctor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        appointment.specialty,
                        color = Color.Gray
                    )
                }

                Text(
                    appointment.status,
                    color = if (upcoming)
                        MaterialTheme.colorScheme.primary
                    else
                        Color.Gray,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    Icons.Default.CalendarMonth,
                    null,
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text("${appointment.date} • ${appointment.time}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    Icons.Default.Schedule,
                    null,
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    appointment.hospital,
                    color = Color.Gray
                )
            }

            if (upcoming) {

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    OutlinedButton(
                        onClick = {}
                    ) {
                        Text("Reschedule")
                    }

                    Button(
                        onClick = {}
                    ) {
                        Text("View")
                    }
                }
            }
        }
    }
}

// ===================== BOOK APPOINTMENT =====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentSheet(
    onDismiss: () -> Unit
) {

    var doctor by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                "Book Appointment",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = doctor,
                onValueChange = { doctor = it },
                label = { Text("Doctor Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date") },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = reason,
                onValueChange = { reason = it },
                label = { Text("Reason") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onDismiss() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(16.dp)
            ) {

                Text(
                    "Confirm Appointment",
                    fontSize = 17.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// ===================== PREVIEW =====================

@Preview(showBackground = true)
@Composable
fun AppointmentPreview() {
    MaterialTheme {
        AppointmentScreen()
    }
}