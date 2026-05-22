package com.example.medcare.login_signup

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun AuthScreen(navController: NavHostController) {

    var isLogin by remember { mutableStateOf(true) }

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val selectedColor by animateColorAsState(
        if (isLogin) MaterialTheme.colorScheme.primary
        else Color(0xFF00A86B),
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFF4F8FF),
                        Color.White,
                        Color(0xFFE8F5E9)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            // LOGO SECTION
            Surface(
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                shadowElevation = 12.dp,
                color = selectedColor
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.HealthAndSafety,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(52.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "MedCare",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = selectedColor
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = if (isLogin)
                    "Welcome back to your health companion"
                else
                    "Create your health account today",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // LOGIN / SIGNUP SWITCH
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color(0xFFF1F3F6))
                    .padding(6.dp)
            ) {

                AuthSwitchButton(
                    text = "Login",
                    selected = isLogin,
                    onClick = { isLogin = true },
                    modifier = Modifier.weight(1f)
                )

                AuthSwitchButton(
                    text = "Sign Up",
                    selected = !isLogin,
                    onClick = { isLogin = false },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            // FORM CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    AnimatedContent(
                        targetState = isLogin,
                        label = ""
                    ) { loginMode ->

                        Column {

                            if (!loginMode) {

                                AuthTextField(
                                    value = fullName,
                                    onValueChange = { fullName = it },
                                    label = "Full Name",
                                    placeholder = "Enter your full name",
                                    icon = Icons.Default.Person
                                )

                                Spacer(modifier = Modifier.height(18.dp))

                                AuthTextField(
                                    value = phone,
                                    onValueChange = { phone = it },
                                    label = "Phone Number",
                                    placeholder = "+254 700 000 000",
                                    icon = Icons.Default.Phone,
                                    keyboardType = KeyboardType.Phone
                                )

                                Spacer(modifier = Modifier.height(18.dp))
                            }

                            AuthTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = "Email Address",
                                placeholder = "example@email.com",
                                icon = Icons.Default.Email,
                                keyboardType = KeyboardType.Email
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(18.dp),
                                label = { Text("Password") },
                                placeholder = { Text("Enter password") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null
                                    )
                                },
                                visualTransformation = PasswordVisualTransformation(),
                                singleLine = true
                            )

                            if (loginMode) {

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "Forgot Password?",
                                    modifier = Modifier
                                        .align(Alignment.End)
                                        .clickable { },
                                    color = selectedColor,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            // MAIN BUTTON
                            Button(
                                onClick = {

                                    navController.navigate("home") {
                                        popUpTo("onboarding") {
                                            inclusive = true
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(58.dp),
                                shape = RoundedCornerShape(18.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = selectedColor
                                )
                            ) {

                                Text(
                                    text = if (loginMode)
                                        "Sign In"
                                    else
                                        "Create Account",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            // GOOGLE BUTTON
                            OutlinedButton(
                                onClick = { },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(58.dp),
                                shape = RoundedCornerShape(18.dp)
                            ) {

                                Text(
                                    text = "Continue with Google",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // FOOTER
            Text(
                text = "Secure • Trusted • Smart Healthcare",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// -----------------------------------
// SWITCH BUTTON
// -----------------------------------

@Composable
fun AuthSwitchButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(
                if (selected)
                    MaterialTheme.colorScheme.primary
                else
                    Color.Transparent
            )
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = text,
            color = if (selected) Color.White else Color.Gray,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

// -----------------------------------
// CUSTOM TEXT FIELD
// -----------------------------------

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        label = {
            Text(label)
        },
        placeholder = {
            Text(placeholder)
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true
    )
}

// -----------------------------------
// PREVIEW
// -----------------------------------

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AuthPreview() {

    MaterialTheme {

        AuthScreen(
            navController = rememberNavController()
        )
    }
}