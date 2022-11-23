package com.catnip.animecommunity.data.firebase.model

import androidx.annotation.Keep
import java.util.*

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Keep
data class SubThreadItem(
    var id : String? = "",
    val creator: User? = null,
    val content: String = ""
)

