package com.jhorgi.registermenu.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhorgi.registermenu.data.MainRepository
import com.jhorgi.registermenu.ui.screen.home.HomeViewModel
import com.jhorgi.registermenu.ui.screen.inputEmailForgotPassword.EmailForgotPasswordViewModel
import com.jhorgi.registermenu.ui.screen.inputOtp.InputOtpViewModel
import com.jhorgi.registermenu.ui.screen.login.LoginViewModel
import com.jhorgi.registermenu.ui.screen.register.RegisterViewModel
import com.jhorgi.registermenu.ui.screen.resetPasswordForgot.ResetPasswordViewModel

class ViewModelFactory(private val repository: MainRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(EmailForgotPasswordViewModel::class.java)) {
            return EmailForgotPasswordViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(InputOtpViewModel::class.java)) {
            return InputOtpViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(ResetPasswordViewModel::class.java)) {
            return ResetPasswordViewModel(repository) as T
        }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}