package com.catnip.animecommunity.presentation.ui.thread

import androidx.core.view.isVisible
import com.catnip.animecommunity.R
import com.catnip.animecommunity.base.BaseViewModelFragment
import com.catnip.animecommunity.databinding.FragmentThreadBinding
import com.catnip.animecommunity.presentation.adapter.ThreadListAdapter
import com.catnip.animecommunity.presentation.ui.threadform.ThreadFormBottomSheet
import org.koin.androidx.viewmodel.ext.android.viewModel

class ThreadFragment :
    BaseViewModelFragment<FragmentThreadBinding, ThreadViewModel>(FragmentThreadBinding::inflate) {
    override val viewModel: ThreadViewModel by viewModel()

    private val adapter: ThreadListAdapter by lazy {
        ThreadListAdapter(viewModel.getThreadStreamData(),
            onDataExist = {
                showData()
            },
            onLoading = {
                showLoading(it)
            },
            onDataError = {
                showError()
                setErrorMessage(it.message.orEmpty())
            },
            onDataEmpty = {
                showEmptyData()
            })
    }

    override fun initView() {
        super.initView()
        binding.fabAdd.setOnClickListener {
            openCreateThreadBottomSheet()
        }
        binding.rvThread.adapter = this.adapter
    }
    override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.rvThread.isVisible = !isShowLoading
        binding.tvErrorHome.isVisible = !isShowLoading
        binding.pbHome.isVisible = isShowLoading
    }

    private fun showError() {
        binding.rvThread.isVisible = false
        binding.pbHome.isVisible = false
        binding.tvErrorHome.isVisible = true
    }

    private fun showData() {
        binding.rvThread.isVisible = true
        binding.pbHome.isVisible = false
        binding.tvErrorHome.isVisible = false
    }

    private fun showEmptyData() {
        showError()
        setErrorMessage(getText(R.string.text_empty_data).toString())
    }

    private fun setErrorMessage(msg: String) {
        binding.tvErrorHome.text = msg
    }

    private fun openCreateThreadBottomSheet() {
        ThreadFormBottomSheet().show(childFragmentManager, null)
    }
}
