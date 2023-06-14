package com.jhorgi.registermenu.ui.navigation.nav_graph

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.jhorgi.registermenu.ui.navigation.AUTH_GRAPH_ROUTE
import com.jhorgi.registermenu.ui.navigation.HOME_GRAPH_ROUTE
import com.jhorgi.registermenu.ui.navigation.Screen
import com.jhorgi.registermenu.ui.screen.inputEmailForgotPassword.InputEmailForgotPasswordScreen
import com.jhorgi.registermenu.ui.screen.inputOtp.InputOtpScreen
import com.jhorgi.registermenu.ui.screen.login.LoginScreen
import com.jhorgi.registermenu.ui.screen.register.RegisterScreen
import com.jhorgi.registermenu.ui.screen.resetPasswordForgot.ResetPasswordScreen


fun NavGraphBuilder.authNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = Screen.Login.route,
        route = AUTH_GRAPH_ROUTE
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                navigateToRegister = { navController.navigate(Screen.Register.route) },
                navigateToForgotPassword = { navController.navigate(Screen.EmailForgotPassword.route) },
                navigateToHome = {
                    navController.popBackStack()
                    navController.navigate(HOME_GRAPH_ROUTE)
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                navigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.EmailForgotPassword.route) {
            InputEmailForgotPasswordScreen(navigateToInputOTP = { navController.navigate(Screen.InputOtp.route) })
        }

        composable(Screen.InputOtp.route) {
            InputOtpScreen(
                navigateToResetPassword = {key->
                    navController.navigate(Screen.ResetPassword.createRoute(key))
                }
            )
        }

        composable(
            route = Screen.ResetPassword.route,
            arguments = listOf(navArgument("key") { type = NavType.StringType })
        ) {
            val keyArg = it.arguments?.getString("key")
            if (keyArg != null) {
                ResetPasswordScreen(
                    key = keyArg,
                    navigateToLogin = {
                        navController.navigate(Screen.Login.route){
                            popUpTo(Screen.Login.route){inclusive=true}
                        }
                    }
                )
            }


//            ResetPasswordScreen(
//                navigateToLogin = {
//                    navController.popBackStack()
//                }
//            )



        }


    }

}