package com.jhorgi.koma.ui.screen.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ArgumentModel (
    var fullName: String?,
    var height: String?,
    var weight: String?,
    var phoneNumber: String?,
): Parcelable
