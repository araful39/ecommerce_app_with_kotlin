package com.example.bdskills_app.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bdskills_app.ui.components.AuthFooterLink
import com.example.bdskills_app.ui.components.AuthScreenLayout
import com.example.bdskills_app.ui.components.CommonButton
import com.example.bdskills_app.ui.components.CommonTextField
import com.example.bdskills_app.ui.components.ValidationErrorDialog

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }

    if (showErrorDialog) {
        ValidationErrorDialog(onDismiss = { showErrorDialog = false })
    }

    AuthScreenLayout(title = "Login") {
        CommonTextField(
            value = username,
            onValueChange = { username = it },
            label = "Username"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CommonTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        CommonButton(
            text = "Login",
            onClick = {
                if (username.isNotBlank() && password.isNotBlank()) {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                } else {
                    showErrorDialog = true
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        AuthFooterLink(
            text = "Don't have an account? Register",
            onClick = { navController.navigate("register") }
        )
    }
}
