package com.maycon.taskeasy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.maycon.taskeasy.view.AppRoutes
import com.maycon.taskeasy.view.HomeScreen
import com.maycon.taskeasy.view.LoginScreen
import com.maycon.taskeasy.view.RegisterScreen
import com.maycon.taskeasy.ui.theme.TaskEasyTheme
import com.maycon.taskeasy.viewmodel.TarefaViewModel
import com.maycon.taskeasy.viewmodel.TarefaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskEasyTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Pega a 'Application' atual para acessar o Repositório
    val application = (LocalContext.current.applicationContext as TaskEasyApplication)

    // Instancia a ViewModel de Tarefas usando a Factory
    val tarefaViewModel: TarefaViewModel = viewModel(
        factory = TarefaViewModelFactory(application.repository)
    )

    // O 'userId' foi REMOVIDO daqui de cima

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
                onNavigateToHome = { userId -> // O 'userId' é recebido do LoginScreen
                    // Navega passando o ID na rota
                    navController.navigate("${AppRoutes.HOME_SCREEN_ROUTE}/$userId") {
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
        composable(
            route = AppRoutes.HOME_SCREEN, // Agora é "home/{userId}"
            arguments = listOf(navArgument(AppRoutes.HOME_ARG_USER_ID) { // Define o argumento
                type = NavType.StringType
            })
        ) { backStackEntry -> // Renomeado 'it' para 'backStackEntry'

            // Pega o ID do argumento da rota. ADEUS "id_invalido"!
            val userId = backStackEntry.arguments?.getString(AppRoutes.HOME_ARG_USER_ID)

            HomeScreen(
                tarefaViewModel = tarefaViewModel,
                userId = userId ?: "id_falhou_navegacao", // Se falhar, é erro de rota
                onNavigateToLogin = {
                    // Navega para o Login e limpa a pilha (Logout)
                    navController.navigate(AppRoutes.LOGIN_SCREEN) {
                        popUpTo(AppRoutes.HOME_SCREEN) { inclusive = true }
                    }
                }
            )
        }
    }
}