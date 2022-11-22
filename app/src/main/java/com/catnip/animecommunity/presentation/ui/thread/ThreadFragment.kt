package com.catnip.animecommunity.presentation.ui.thread

import com.catnip.animecommunity.base.BaseViewModelFragment
import com.catnip.animecommunity.databinding.FragmentThreadBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ThreadFragment :
    BaseViewModelFragment<FragmentThreadBinding, ThreadViewModel>(FragmentThreadBinding::inflate) {
    override val viewModel: ThreadViewModel by viewModel()

}
