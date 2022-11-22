package com.catnip.animecommunity.data.network.api.model

import com.google.gson.annotations.SerializedName

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
data class Anime(
    @SerializedName("animeId")
    val animeId : String,
    @SerializedName("animeTitle")
    val animeTitle : String,
    @SerializedName("animeImg")
    val animeImg : String,
    @SerializedName("releasedDate")
    val releasedDate : String,
    @SerializedName("animeUrl")
    val animeUrl : String,
)
