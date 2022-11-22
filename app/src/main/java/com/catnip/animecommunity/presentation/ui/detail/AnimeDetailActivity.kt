package com.catnip.animecommunity.presentation.ui.detail

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import coil.load
import com.catnip.animecommunity.R
import com.catnip.animecommunity.base.BaseViewModelActivity
import com.catnip.animecommunity.base.wrapper.Resource
import com.catnip.animecommunity.data.network.api.model.AnimeDetail
import com.catnip.animecommunity.databinding.ActivityAnimeDetailBinding
import com.catnip.animecommunity.presentation.adapter.EpisodesAdapter
import com.catnip.animecommunity.presentation.ui.webdetail.WebDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AnimeDetailActivity : BaseViewModelActivity<ActivityAnimeDetailBinding, AnimeDetailViewModel>(
    ActivityAnimeDetailBinding::inflate
) {
    override val viewModel: AnimeDetailViewModel by viewModel {
        parametersOf(intent.extras ?: bundleOf())
    }

    private val episodeAdapter: EpisodesAdapter by lazy {
        EpisodesAdapter {
            WebDetailActivity.startActivity(
                this,
                viewModel.detailResult.value?.payload?.animeTitle.orEmpty(),
                it.episodeUrl
            )
        }
    }

    override fun initView() {
        super.initView()
        setupToolbar()
        viewModel.fetchDetail()
        setupEpisodeList()
    }

    private fun setupToolbar() {
        title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupEpisodeList() {
        binding.rvEpisode.adapter = episodeAdapter
    }


    override fun observeData() {
        super.observeData()
        viewModel.detailResult.observe(this) {
            when (it) {
                is Resource.Empty -> {
                    //do nothing
                }
                is Resource.Error -> {
                    showError()
                    setErrorMessage(it.exception?.message.orEmpty())
                }
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    it.payload?.let { detail ->
                        showData(detail)
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.pbDetail.isVisible = true
        binding.tvErrorDetail.isVisible = false
        binding.groupContent.isVisible = false
    }
    private fun showError() {
        binding.pbDetail.isVisible = false
        binding.tvErrorDetail.isVisible = true
        binding.groupContent.isVisible = false
    }

    private fun setErrorMessage(msg: String) {
        binding.tvErrorDetail.text = msg
    }

    private fun showData(data: AnimeDetail) {
        binding.pbDetail.isVisible = false
        binding.tvErrorDetail.isVisible = false
        binding.groupContent.isVisible = true
        bindData(data)
    }

    private fun bindData(data: AnimeDetail) {
        binding.ivPoster.load(data.animeImg) {
            placeholder(R.drawable.ic_placeholder_poster)
        }
        title = data.animeTitle
        binding.tvTitleAnime.text = data.animeTitle
        binding.tvStatusAnime.text = data.status
        binding.tvSynopsisAnime.text = data.synopsis
        binding.tvReleaseDateAnime.text = data.releasedDate
        binding.tvGenreAnime.text = data.genres.joinToString(separator = ",")
        episodeAdapter.setItems(data.episodesList)
    }

    companion object {
        const val EXTRAS_ANIME_ID = "EXTRAS_ANIME_ID"
        fun startActivity(context: Context, animeId: String) {
            context.startActivity(Intent(context, AnimeDetailActivity::class.java).apply {
                putExtra(EXTRAS_ANIME_ID, animeId)
            })
        }
    }

}