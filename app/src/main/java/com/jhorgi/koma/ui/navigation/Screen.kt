package com.jhorgi.koma.ui.navigation

import android.os.Parcelable
import com.jhorgi.koma.data.remote.response.GenericResponse


const val ROOT_GRAPH_ROUTE = "root_route"
const val AUTH_GRAPH_ROUTE = "auth_route"
const val HOME_GRAPH_ROUTE = "home_route"
const val SPLASH_GRAPH_ROUTE = "splash_route"

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Camera: Screen("camera")
    object Profile: Screen("profile")
    object EditProfileDetail: Screen("profile/{name}/{height}/{weight}/{phonenum}"){
        fun createRoute(name: String, height:String, weight:String, phonenum:String )= "profile/${name}/${height}/${weight}/${phonenum}"
    }
    object Bookmark : Screen("bookmark")

    object ChangePassword : Screen("change_password")
    object Loading : Screen("loading")
    object Result : Screen("camera/{ingredient}") {
        fun createRoute(ingredient : String) = "camera/$ingredient"
    }
    object Detail : Screen("home/{Id}"){
        fun createRoute(Id: String)= "home/$Id"
    }

//    Auth
    object Login : Screen("login")
    object Register: Screen("register")
    object InputOtp: Screen("inputOtp")
    object EmailForgotPassword: Screen("emailForgotPassword")
    object ResetPassword: Screen("inputOtp/{key}"){
        fun createRoute(key : String) = "inputOtp/$key"
    }

    //    Splash
    object Splash : Screen("Splash")
}