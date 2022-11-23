package com.catnip.animecommunity.data.firebase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Parcelize
data class User(
    val displayName: String = "",
    val email: String = "",
    val photoProfileUrl: String = ""
) : Parcelable