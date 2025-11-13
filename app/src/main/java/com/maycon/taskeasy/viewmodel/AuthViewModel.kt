package com.maycon.taskeasy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// Guarda o estado da UI para autenticação
data class AuthUiState(
    val email: String = "",
    val senha: String = "",
    val mensagemErro: String? = null,
    val isLoading: Boolean = false,
    val loginSucesso: Boolean = false,
    val registroSucesso: Boolean = false
)

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    // Funções chamadas pela UI para atualizar o estado
    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onSenhaChange(senha: String) {
        _uiState.update { it.copy(senha = senha) }
    }

    // Função de Login
    fun login() {
        if (!validarCampos()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mensagemErro = null) }
            try {
                auth.signInWithEmailAndPassword(
                    _uiState.value.email,
                    _uiState.value.senha
                ).await() // Espera a tarefa do Firebase terminar

                // Sucesso
                _uiState.update { it.copy(isLoading = false, loginSucesso = true) }
            } catch (e: FirebaseAuthException) {
                // Trata erros comuns do Firebase
                val erroMsg = when (e.errorCode) {
                    "ERROR_INVALID_EMAIL" -> "E-mail inválido."
                    "ERROR_WRONG_PASSWORD" -> "Senha incorreta."
                    "ERROR_USER_NOT_FOUND" -> "Usuário não encontrado."
                    else -> "Erro ao fazer login."
                }
                _uiState.update { it.copy(isLoading = false, mensagemErro = erroMsg) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, mensagemErro = e.message) }
            }
        }
    }

    // Função de Registro
    fun registrar() {
        if (!validarCampos()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mensagemErro = null) }
            try {
                auth.createUserWithEmailAndPassword(
                    _uiState.value.email,
                    _uiState.value.senha
                ).await()

                // Sucesso
                _uiState.update { it.copy(isLoading = false, registroSucesso = true) }
            } catch (e: FirebaseAuthException) {
                val erroMsg = when (e.errorCode) {
                    "ERROR_EMAIL_ALREADY_IN_USE" -> "E-mail já cadastrado."
                    "ERROR_WEAK_PASSWORD" -> "Senha fraca (mínimo 6 caracteres)."
                    else -> "Erro ao registrar."
                }
                _uiState.update { it.copy(isLoading = false, mensagemErro = erroMsg) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, mensagemErro = e.message) }
            }
        }
    }

    // Limpa o estado de sucesso (para a UI não navegar de novo)
    fun onLoginSuccessHandled() {
        _uiState.update { it.copy(loginSucesso = false, email = "", senha = "") }
    }

    fun onRegisterSuccessHandled() {
        _uiState.update { it.copy(registroSucesso = false, email = "", senha = "") }
    }

    private fun validarCampos(): Boolean {
        if (_uiState.value.email.isBlank() || _uiState.value.senha.isBlank()) {
            _uiState.update { it.copy(mensagemErro = "Preencha todos os campos.") }
            return false
        }
        return true
    }
}