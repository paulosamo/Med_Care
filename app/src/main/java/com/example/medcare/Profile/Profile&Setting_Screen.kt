package com.example.medcare.Profile


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ======================= DATA MODELS =======================

data class MedicalVisit(
    val date: String,
    val doctor: String,
    val diagnosis: String,
    val prescription: String,
    val notes: String
)

data class LabResult(
    val test: String,
    val result: String,
    val status: String
)

data class Allergy(
    val name: String,
    val severity: String
)

// ======================= SAMPLE DATA =======================

val visits = listOf(
    MedicalVisit(
        "21 May 2026",
        "Dr. Innocent Mose",
        "Hypertension",
        "Amlodipine 5mg",
        "Patient advised to reduce salt intake"
    ),
    MedicalVisit(
        "10 May 2026",
        "Dr. James Mwangi",
        "Malaria",
        "Artemether-Lumefantrine",
        "Full recovery expected"
    )
)

val labs = listOf(
    LabResult("Blood Sugar", "6.2 mmol/L", "Normal"),
    LabResult("Hemoglobin", "12.5 g/dL", "Slightly Low"),
    LabResult("WBC Count", "Normal", "Normal")
)

val allergies = listOf(
    Allergy("Penicillin", "High"),
    Allergy("Dust", "Mild")
)

// ======================= MAIN EMR SCREEN =======================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientEMRScreen() {

    var tab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Patient Medical Record (EMR)") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            // PATIENT HEADER
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("👨🏾‍⚕️ Paul Osamo", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Patient ID: MED-2026-001")
                    Text("Blood Group: O+")
                    Text("Age: 22")
                }
            }

            Spacer(Modifier.height(12.dp))

            // TABS
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { tab = 0 }) { Text("History") }
                Button(onClick = { tab = 1 }) { Text("Labs") }
                Button(onClick = { tab = 2 }) { Text("Allergies") }
            }

            Spacer(Modifier.height(12.dp))

            when (tab) {
                0 -> VisitHistory()
                1 -> LabResults()
                2 -> AllergyScreen()
            }
        }
    }
}

// ======================= VISIT HISTORY =======================

@Composable
fun VisitHistory() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(visits) { v ->

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(14.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocalHospital, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(v.date, fontWeight = FontWeight.Bold)
                    }

                    Spacer(Modifier.height(6.dp))

                    Text("Doctor: ${v.doctor}")
                    Text("Diagnosis: ${v.diagnosis}")
                    Text("Prescription: ${v.prescription}")
                    Text("Notes: ${v.notes}")
                }
            }
        }
    }
}

// ======================= LAB RESULTS =======================

@Composable
fun LabResults() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(labs) { l ->

            Card(shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(14.dp)) {

                    Text(l.test, fontWeight = FontWeight.Bold)
                    Text("Result: ${l.result}")
                    Text("Status: ${l.status}")
                }
            }
        }
    }
}

// ======================= ALLERGIES =======================

@Composable
fun AllergyScreen() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

        allergies.forEach { a ->

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                        if (a.severity == "High")
                            MaterialTheme.colorScheme.errorContainer
                        else
                            MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(Icons.Default.Warning, contentDescription = null)
                    Spacer(Modifier.width(10.dp))

                    Column {
                        Text(a.name, fontWeight = FontWeight.Bold)
                        Text("Severity: ${a.severity}")
                    }
                }
            }
        }
    }
}

// ======================= PREVIEW =======================

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EMRPreview() {
    PatientEMRScreen()
}