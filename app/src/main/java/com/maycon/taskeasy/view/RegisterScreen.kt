package com.maycon.taskeasy.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maycon.taskeasy.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    // Reutilizamos a mesma ViewModel
    authViewModel: AuthViewModel = viewModel()
) {
    val uiState by authViewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Observa se o registro teve sucesso para navegar
    LaunchedEffect(key1 = uiState.registroSucesso) {
        if (uiState.registroSucesso) {
            Toast.makeText(context, "Registro bem-sucedido!", Toast.LENGTH_SHORT).show()
            onNavigateBack() // Volta para a tela de Login
            authViewModel.onRegisterSuccessHandled() // Limpa o estado
        }
    }

    // Observa mensagens de erro para mostrar Toast
    LaunchedEffect(key1 = uiState.mensagemErro) {
        if (uiState.mensagemErro != null) {
            Toast.makeText(context, uiState.mensagemErro, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "TaskEasy - Registro", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { authViewModel.onEmailChange(it) },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.senha,
            onValueChange = { authViewModel.onSenhaChange(it) },
            label = { Text("Senha (mín. 6 caracteres)") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(32.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { authViewModel.registrar() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onNavigateBack) {
            Text("Já tem conta? Voltar para o Login")
        }
    }
}