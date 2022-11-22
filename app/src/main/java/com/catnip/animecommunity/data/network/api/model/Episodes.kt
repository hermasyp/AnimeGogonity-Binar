package com.catnip.animecommunity.data.network.api.model


import com.google.gson.annotations.SerializedName

data class Episodes(
    @SerializedName("episodeId")
    val episodeId: String,
    @SerializedName("episodeNum")
    val episodeNum: String,
    @SerializedName("episodeUrl")
    val episodeUrl: String
)