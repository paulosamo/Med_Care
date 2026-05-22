package com.example.medcare.Billing


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ======================= DATA MODELS =======================

data class Patient(val name: String, val age: Int)
data class Doctor(val name: String, val spec: String)
data class Appointment(val patient: String, val doctor: String, val status: String)
data class Bill(val item: String, val amount: Int)

// ======================= SAMPLE DATA =======================

val patients = listOf(
    Patient("John Doe", 45),
    Patient("Mary Wanjiku", 32),
    Patient("Peter Otieno", 28)
)

val doctors = listOf(
    Doctor("Dr. Innocent Mose", "WoundSpecialist"),
    Doctor("Dr. James Mwangi", "General Physician")
)

val appointments = listOf(
    Appointment("John Doe", "Dr. Innocent Mose", "BOOKED"),
    Appointment("Mary Wanjiku", "Dr. James Mwangi", "COMPLETED")
)

val bills = listOf(
    Bill("Consultation", 2000),
    Bill("Lab Test", 3500),
    Bill("Medication", 1200)
)

// ======================= MAIN ERP SCREEN =======================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedCareERP() {

    var tab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("MedCare Hospital") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(12.dp)
        ) {

            // TAB MENU
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { tab = 0 }) { Text("Dashboard") }
                Button(onClick = { tab = 1 }) { Text("Patients") }
                Button(onClick = { tab = 2 }) { Text("Doctors") }
                Button(onClick = { tab = 3 }) { Text("Billing") }
                Button(onClick = { tab = 4 }) { Text("Appointments") }
            }

            Spacer(Modifier.height(16.dp))

            when (tab) {
                0 -> DashboardScreen()
                1 -> PatientScreen()
                2 -> DoctorScreen()
                3 -> BillingScreen()
                4 -> AppointmentScreen()
            }
        }
    }
}

// ======================= DASHBOARD =======================

@Composable
fun DashboardScreen() {
    Column {
        Text("Hospital Overview", fontSize = 22.sp)

        Spacer(Modifier.height(10.dp))

        Card {
            Column(Modifier.padding(12.dp)) {
                Text("Total Patients: ${patients.size}")
                Text("Doctors Available: ${doctors.size}")
                Text("Appointments: ${appointments.size}")
                Text("Pending Bills: ${bills.size}")
            }
        }
    }
}

// ======================= PATIENTS =======================

@Composable
fun PatientScreen() {
    Column {
        Text("Patients", fontSize = 20.sp)

        LazyColumn {
            items(patients) { p ->
                Card(Modifier.fillMaxWidth().padding(6.dp)) {
                    Column(Modifier.padding(10.dp)) {
                        Text(p.name)
                        Text("Age: ${p.age}")
                    }
                }
            }
        }
    }
}

// ======================= DOCTORS =======================

@Composable
fun DoctorScreen() {
    Column {
        Text("Doctors", fontSize = 20.sp)

        LazyColumn {
            items(doctors) { d ->
                Card(Modifier.fillMaxWidth().padding(6.dp)) {
                    Column(Modifier.padding(10.dp)) {
                        Text(d.name)
                        Text(d.spec)
                    }
                }
            }
        }
    }
}

// ======================= BILLING =======================

@Composable
fun BillingScreen() {

    var selected by remember { mutableStateOf<Bill?>(null) }

    Column {
        Text("Billing System", fontSize = 20.sp)

        LazyColumn {
            items(bills) { b ->
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Column(Modifier.padding(10.dp)) {
                        Text(b.item)
                        Text("KSh ${b.amount}")

                        Button(onClick = { selected = b }) {
                            Text("Pay with M-Pesa")
                        }
                    }
                }
            }
        }
    }

    // PAYMENT DIALOG (SIMULATED MPESA)
    if (selected != null) {
        AlertDialog(
            onDismissRequest = { selected = null },
            title = { Text("M-Pesa Payment") },
            text = {
                Column {
                    Text("Paying for: ${selected!!.item}")
                    Text("Amount: KSh ${selected!!.amount}")
                    Text("📲 STK Push sent to phone (SIMULATED)")
                }
            },
            confirmButton = {
                Button(onClick = { selected = null }) {
                    Text("Done")
                }
            }
        )
    }
}

// ======================= APPOINTMENTS =======================

@Composable
fun AppointmentScreen() {
    Column {
        Text("Appointments", fontSize = 20.sp)

        LazyColumn {
            items(appointments) { a ->
                Card(Modifier.fillMaxWidth().padding(6.dp)) {
                    Column(Modifier.padding(10.dp)) {
                        Text("Patient: ${a.patient}")
                        Text("Doctor: ${a.doctor}")
                        Text("Status: ${a.status}")
                    }
                }
            }
        }
    }
}

// ======================= PREVIEW =======================

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MedCareERPPreview() {
    MedCareERP()
}