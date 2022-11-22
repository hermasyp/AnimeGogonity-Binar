package com.catnip.animecommunity.data.firebase.model

import androidx.annotation.Keep

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Keep
data class ThreadItem(
    val creator: User? = null,
    val title: String = "",
    val content: String = ""
)
