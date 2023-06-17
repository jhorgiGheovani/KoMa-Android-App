package com.jhorgi.koma.ui.navigation.nav_graph


import androidx.activity.compose.BackHandler
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.jhorgi.koma.ui.navigation.AUTH_GRAPH_ROUTE
import com.jhorgi.koma.ui.navigation.HOME_GRAPH_ROUTE
import com.jhorgi.koma.ui.navigation.Screen
import com.jhorgi.koma.ui.screen.forgotpass.InputEmailForgotPasswordScreen
import com.jhorgi.koma.ui.screen.inputotp.InputOtpScreen
import com.jhorgi.koma.ui.screen.login.LoginScreen
import com.jhorgi.koma.ui.screen.regiter.RegisterScreen
import com.jhorgi.koma.ui.screen.resetpass.ResetPasswordScreen


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
                },
                onOtpChanged = {}
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
            BackHandler {
                navController.navigate(Screen.Login.route){
                    popUpTo(Screen.Login.route){
                        inclusive=true
                    }
                }
            }
//            ResetPasswordScreen(
//                navigateToLogin = {
//                    navController.popBackStack()
//                }
//            )

        }

    }

}