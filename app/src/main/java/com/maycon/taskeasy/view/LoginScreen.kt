package com.maycon.taskeasy.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    Text(text = "Eu sou a Tela de Login")
}