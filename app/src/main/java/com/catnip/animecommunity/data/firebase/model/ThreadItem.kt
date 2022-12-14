package com.catnip.animecommunity.data.firebase.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Keep
@Parcelize
data class ThreadItem(
    var id: String = "",
    val creator: User? = null,
    val title: String = "",
    val content: String = ""
) : Parcelable

