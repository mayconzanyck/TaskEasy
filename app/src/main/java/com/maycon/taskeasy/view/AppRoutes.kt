package com.maycon.taskeasy.view

object AppRoutes {
    const val LOGIN_SCREEN = "login"
    const val REGISTER_SCREEN = "register"

    // Define a rota da Home com um "argumento" (um espaço para o ID)
    const val HOME_SCREEN_ROUTE = "home"
    const val HOME_ARG_USER_ID = "userId"
    const val HOME_SCREEN = "$HOME_SCREEN_ROUTE/{$HOME_ARG_USER_ID}"
}