package com.catnip.animecommunity.presentation.ui.webdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.os.bundleOf
import com.catnip.animecommunity.R
import com.catnip.animecommunity.base.BaseViewModelActivity
import com.catnip.animecommunity.base.wrapper.Resource
import com.catnip.animecommunity.databinding.ActivityWebDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WebDetailActivity : BaseViewModelActivity<ActivityWebDetailBinding, WebDetailViewModel>(
    ActivityWebDetailBinding::inflate
) {
    override val viewModel: WebDetailViewModel by viewModel {
        parametersOf(intent.extras ?: bundleOf())
    }

    override fun initView() {
        super.initView()
        setupToolbar()
        setupWebView()
        viewModel.loadUrl()
    }

    override fun observeData() {
        super.observeData()
        viewModel.urlResult.observe(this) {
            when (it) {
                is Resource.Empty -> {
                    Toast.makeText(this, getString(R.string.text_url_empty), Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Error -> {
                    //do nothing
                }
                is Resource.Loading -> {
                    //do nothing
                }
                is Resource.Success -> it.payload?.let { url -> loadUrl(url) }
            }
        }
    }

    private fun setupToolbar() {
        title = intent.extras?.getString(EXTRAS_ANIME_NAME).orEmpty()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.wvPage.settings.javaScriptEnabled = true
        binding.wvPage.settings.allowContentAccess = true
        binding.wvPage.settings.domStorageEnabled = true
        binding.wvPage.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        binding.wvPage.webChromeClient = ProtectedMediaChromeClient()
    }

    private fun loadUrl(url: String) {
        binding.wvPage.loadUrl(url)
    }

    private fun checkIfWebViewHasBackstack(): Boolean {
        return binding.wvPage.canGoBack()
    }

    override fun onBackPressed() {
        if (checkIfWebViewHasBackstack()) {
            binding.wvPage.goBack()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        const val EXTRAS_ANIME_EPS_URL = "EXTRAS_ANIME_EPS_URL"
        const val EXTRAS_ANIME_NAME = "EXTRAS_ANIME_NAME"
        fun startActivity(context: Context, animeTitle: String, url: String) {
            context.startActivity(Intent(context, WebDetailActivity::class.java).apply {
                putExtra(EXTRAS_ANIME_NAME, animeTitle)
                putExtra(EXTRAS_ANIME_EPS_URL, url)
            })
        }
    }
}