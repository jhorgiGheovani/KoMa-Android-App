package com.jhorgi.koma.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhorgi.koma.data.MainRepository
import com.jhorgi.koma.ui.screen.bookmark.BookmarkViewModel
import com.jhorgi.koma.ui.screen.change_pass.ChangePassViewModel
import com.jhorgi.koma.ui.screen.detail.DetailViewModel
import com.jhorgi.koma.ui.screen.edit_profile.EditProfileViewModel
import com.jhorgi.koma.ui.screen.forgotpass.EmailForgotPasswordViewModel
import com.jhorgi.koma.ui.screen.home.HomeViewModel
import com.jhorgi.koma.ui.screen.inputotp.InputOtpViewModel
import com.jhorgi.koma.ui.screen.login.LoginViewModel
import com.jhorgi.koma.ui.screen.profile.ProfileViewModel
import com.jhorgi.koma.ui.screen.regiter.RegisterViewModel
import com.jhorgi.koma.ui.screen.resetpass.ResetPasswordViewModel
import com.jhorgi.koma.ui.screen.result.ResultViewModel

class ViewModelFactory(private val repository: MainRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)){
            return BookmarkViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(EmailForgotPasswordViewModel::class.java)) {
            return EmailForgotPasswordViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(InputOtpViewModel::class.java)) {
            return InputOtpViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ResetPasswordViewModel::class.java)) {
            return ResetPasswordViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(ChangePassViewModel::class.java)) {
            return ChangePassViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}