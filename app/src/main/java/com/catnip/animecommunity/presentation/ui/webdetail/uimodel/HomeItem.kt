package com.catnip.animecommunity.presentation.ui.webdetail.uimodel

import androidx.annotation.StringRes
import com.catnip.animecommunity.data.network.api.model.Anime

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
const val HOME_TYPE_HEADER = 0
const val HOME_TYPE_SECTION = 1

sealed class HomeItem(val type: Int) {
    class HomeHeaderItem(val data: Anime) : HomeItem(HOME_TYPE_HEADER)
    class HomeSectionItem(@StringRes val sectionName: Int, val data: List<Anime>) : HomeItem(
        HOME_TYPE_SECTION
    )
}
