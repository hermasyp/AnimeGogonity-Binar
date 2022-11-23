package com.catnip.animecommunity.presentation.ui.threaddetail

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import com.catnip.animecommunity.base.BaseViewModelActivity
import com.catnip.animecommunity.data.firebase.model.ThreadItem
import com.catnip.animecommunity.databinding.ActivityThreadDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ThreadDetailActivity :
    BaseViewModelActivity<ActivityThreadDetailBinding, ThreadDetailViewModel>(
        ActivityThreadDetailBinding::inflate
    ) {

    override val viewModel: ThreadDetailViewModel by viewModel {
        parametersOf(intent.extras ?: bundleOf())
    }


    companion object {
        const val EXTRAS_PARENT_THREAD = "EXTRAS_PARENT_THREAD"
        fun startActivity(context: Context, parentThread: ThreadItem) {
            context.startActivity(Intent(context, ThreadDetailActivity::class.java).apply {
                putExtra(EXTRAS_PARENT_THREAD, parentThread)
            })
        }
    }
}