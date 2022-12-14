package com.catnip.animecommunity.presentation.ui.detail

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.animecommunity.base.wrapper.Resource
import com.catnip.animecommunity.data.repository.AnimeRepository
import com.catnip.animecommunity.data.network.api.model.AnimeDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class AnimeDetailViewModel(private val animeRepository: AnimeRepository, val intentData : Bundle) : ViewModel() {

    val detailResult = MutableLiveData<Resource<AnimeDetail>>()

    fun fetchDetail() {
        val animeId = intentData.getString(AnimeDetailActivity.EXTRAS_ANIME_ID)
        animeId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                detailResult.postValue(Resource.Loading())
                detailResult.postValue(animeRepository.getDetailAnime(animeId))
            }
        }
    }

}