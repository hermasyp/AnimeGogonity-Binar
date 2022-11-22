package com.catnip.animecommunity.data.network.api.model


import com.google.gson.annotations.SerializedName

data class AnimeDetail(
    @SerializedName("animeImg")
    val animeImg: String,
    @SerializedName("animeTitle")
    val animeTitle: String,
    @SerializedName("episodesList")
    val episodesList: List<Episodes>,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("otherNames")
    val otherNames: String,
    @SerializedName("releasedDate")
    val releasedDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("synopsis")
    val synopsis: String,
    @SerializedName("totalEpisodes")
    val totalEpisodes: String,
    @SerializedName("type")
    val type: String
)