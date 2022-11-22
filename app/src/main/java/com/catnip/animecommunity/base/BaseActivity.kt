package com.catnip.animecommunity.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
open class BaseActivity<B : ViewBinding>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity() {

    protected val binding: B by lazy { bindingFactory(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        observeData()
    }

    open fun initView() {

    }

    open fun observeData() {

    }

}

abstract class BaseViewModelActivity<B : ViewBinding, VM : ViewModel>(bindingFactory: (LayoutInflater) -> B) : BaseActivity<B>(bindingFactory){

    abstract val viewModel : VM
}