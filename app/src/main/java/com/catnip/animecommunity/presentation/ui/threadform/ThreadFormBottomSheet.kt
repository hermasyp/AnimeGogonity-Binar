package com.catnip.animecommunity.presentation.ui.threadform

import com.catnip.animecommunity.base.BaseVMBottomSheetFragment
import com.catnip.animecommunity.databinding.FragmentThreadFormBottomSheetBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ThreadFormBottomSheet :
    BaseVMBottomSheetFragment<FragmentThreadFormBottomSheetBinding, ThreadFormViewModel>(
        FragmentThreadFormBottomSheetBinding::inflate
    ) {
    override val viewModel: ThreadFormViewModel by viewModel()


}