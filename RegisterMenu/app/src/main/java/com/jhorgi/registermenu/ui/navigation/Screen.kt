package com.jhorgi.registermenu.ui.navigation


const val ROOT_GRAPH_ROUTE = "root_route"
const val AUTH_GRAPH_ROUTE = "auth_route"
const val HOME_GRAPH_ROUTE = "home_route"


sealed class Screen(val route: String) {

    object Login : Screen("login")

    object Register: Screen("register")

    object InputOtp: Screen("inputOtp")

    object Home: Screen("home")

    object EmailForgotPassword: Screen("emailForgotPassword")


    object ResetPassword: Screen("inputOtp/{key}"){
        fun createRoute(key : String) = "inputOtp/$key"
    }

}