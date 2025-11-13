package com.maycon.taskeasy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.maycon.taskeasy.view.AppRoutes
import com.maycon.taskeasy.view.HomeScreen
import com.maycon.taskeasy.view.LoginScreen
import com.maycon.taskeasy.view.RegisterScreen
import com.maycon.taskeasy.ui.theme.TaskEasyTheme // Importe seu tema

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicando o tema que já veio no projeto (ex: Theme.TaskEasy)
            TaskEasyTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.LOGIN_SCREEN // Tela inicial do App
    ) {
        // Rota para a Tela de Login
        composable(AppRoutes.LOGIN_SCREEN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(AppRoutes.REGISTER_SCREEN)
                },
                onNavigateToHome = {
                    navController.navigate(AppRoutes.HOME_SCREEN) {
                        // Limpa a pilha de navegação para o usuário não voltar para o Login
                        popUpTo(AppRoutes.LOGIN_SCREEN) { inclusive = true }
                    }
                }
            )
        }

        // Rota para a Tela de Registro
        composable(AppRoutes.REGISTER_SCREEN) {
            RegisterScreen(
                onNavigateBack = {
                    navController.popBackStack() // Volta para a tela anterior (Login)
                }
            )
        }

        // Rota para a Tela Principal (Home)
        composable(AppRoutes.HOME_SCREEN) {
            HomeScreen()
        }
    }
}