package com.example.medcare.Medication_Reminders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// ===================== DATA MODELS =====================

data class Medication(
    val id: String,
    val name: String,
    val dosage: String,
    val frequency: String,
    val time: String,
    val remainingDays: Int,
    val taken: Boolean = false,
    val color: Color
)

data class Reminder(
    val time: String,
    val medicine: String,
    val dosage: String
)

// ===================== SAMPLE DATA =====================

fun medicationList() = listOf(

    Medication(
        "1",
        "Amoxicillin",
        "500mg",
        "3 Times Daily",
        "8:00 AM",
        12,
        false,
        Color(0xFF1E88E5)
    ),

    Medication(
        "2",
        "Vitamin D3",
        "2000 IU",
        "Once Daily",
        "9:00 AM",
        30,
        true,
        Color(0xFF9C27B0)
    ),

    Medication(
        "3",
        "Paracetamol",
        "1g",
        "As Needed",
        "2:00 PM",
        7,
        false,
        Color(0xFFFF9800)
    )
)

fun reminderList() = listOf(

    Reminder(
        "8:00 AM",
        "Amoxicillin",
        "500mg - 1 Tablet"
    ),

    Reminder(
        "9:00 AM",
        "Vitamin D3",
        "1 Capsule"
    )
)

// ===================== MAIN SCREEN =====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationsScreen(
    navController: NavHostController? = null
) {

    var medications by remember {
        mutableStateOf(medicationList())
    }

    var showSheet by remember {
        mutableStateOf(false)
    }

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text(
                        "Medications",
                        fontWeight = FontWeight.Bold
                    )
                },

                actions = {

                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, null)
                    }
                }
            )
        },

        floatingActionButton = {

            FloatingActionButton(
                onClick = { showSheet = true }
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

                Spacer(modifier = Modifier.height(8.dp))

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
                            "Track Your Medicines",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Stay healthy by taking medicines on time.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // REMINDERS
            item {

                SectionTitle("Today's Reminders")

                ReminderCard(reminderList())
            }

            // MEDICATIONS
            item {

                SectionTitle("Current Medications")
            }

            items(medications) { medication ->

                MedicationCard(
                    medication = medication,

                    onTaken = {

                        medications = medications.map {

                            if (it.id == medication.id)
                                it.copy(taken = true)
                            else
                                it
                        }
                    }
                )
            }

            // REFILL CARD
            item {

                RefillCard()
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }

    // ADD MEDICATION SHEET
    if (showSheet) {

        AddMedicationSheet(
            onDismiss = { showSheet = false }
        )
    }
}

// ===================== TITLE =====================

@Composable
fun SectionTitle(title: String) {

    Text(
        title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

// ===================== REMINDER CARD =====================

@Composable
fun ReminderCard(reminders: List<Reminder>) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            reminders.forEach { reminder ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            reminder.medicine,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            reminder.dosage,
                            color = Color.Gray
                        )
                    }

                    Text(
                        reminder.time,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// ===================== MEDICATION CARD =====================

@Composable
fun MedicationCard(
    medication: Medication,
    onTaken: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp)
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
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            medication.color.copy(alpha = .15f)
                        ),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        Icons.Default.Medication,
                        null,
                        tint = medication.color,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        medication.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        "${medication.dosage} • ${medication.frequency}",
                        color = Color.Gray
                    )

                    Text(
                        "Time: ${medication.time}",
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "${medication.remainingDays} days left",
                    color = if (medication.remainingDays <= 7)
                        Color.Red
                    else
                        Color.Gray
                )

                Spacer(modifier = Modifier.weight(1f))

                if (medication.taken) {

                    Text(
                        "✓ Taken",
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Bold
                    )
                } else {

                    Button(
                        onClick = onTaken,
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Text("Mark Taken")
                    }
                }
            }
        }
    }
}

// ===================== REFILL CARD =====================

@Composable
fun RefillCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp)
    ) {

        Column(
            modifier = Modifier.padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                Icons.Default.LocalPharmacy,
                null,
                tint = Color(0xFFFF9800),
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Need Refill?",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Request medicine refill directly from your pharmacy.",
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {}) {

                Text("Request Refill")
            }
        }
    }
}

// ===================== ADD MEDICATION SHEET =====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationSheet(
    onDismiss: () -> Unit
) {

    var medicine by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                "Add Medication",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = medicine,
                onValueChange = { medicine = it },
                label = { Text("Medicine Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text("Dosage") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = frequency,
                onValueChange = { frequency = it },
                label = { Text("Frequency") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onDismiss() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(16.dp)
            ) {

                Text("Save Medication")
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// ===================== PREVIEW =====================

@Preview(showBackground = true)
@Composable
fun MedicationsPreview() {

    MaterialTheme {

        MedicationsScreen()
    }
}