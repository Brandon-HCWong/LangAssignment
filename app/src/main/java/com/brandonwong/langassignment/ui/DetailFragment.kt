package com.brandonwong.langassignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.brandonwong.langassignment.R
import com.brandonwong.langassignment.data.`object`.MovieInfo
import com.brandonwong.langassignment.data.provider.ApiInfoProvider
import com.brandonwong.langassignment.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide


class DetailFragment : Fragment()  {
    private var binding: FragmentDetailBinding? = null
    private val movieInfo: MovieInfo? by lazy {
        arguments?.getSerializable(BUNDLE_KEY_MOVIE_INFO) as? MovieInfo
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("BrandonDebug", "[DetailFragment - onCreateView] : movieInfo = $movieInfo")
        return with(FragmentDetailBinding.inflate(inflater, container, false)) {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initView()
    }

    private fun initToolbar() {
        binding?.run {
            toolbar.title = getString(R.string.detail_toolbar_title)
            toolbar.setNavigationIcon(R.drawable.ic_back_button)
            toolbar.setNavigationOnClickListener {
                NavHostFragment.findNavController(this@DetailFragment).navigateUp()
            }
        }
    }

    private fun initView() {
        binding?.run {
            movieInfo?.let { movieInfo ->
                tvTitleContent.text = movieInfo.originalTitle
                tvDescription.text = movieInfo.description
                Glide.with(root)
                    .load("${ApiInfoProvider.geTmdbImageBaseUrl()}${movieInfo.posterPath}")
                    .placeholder(R.drawable.ic_photo_placeholder)
                    .into(ivPoster)
                when (movieInfo.isFavorite) {
                    true -> {
                        ivFavorite.setImageResource(R.drawable.ic_favourite)
                    }
                    false -> {
                        ivFavorite.setImageResource(R.drawable.ic_unfavourite)
                    }
                }
            }
        }
    }


    companion object {
        private const val BUNDLE_KEY_MOVIE_INFO = "bundle_key_movie_info"

        fun generateArguments(movieInfo: MovieInfo): Bundle {
            return Bundle().apply {
                putSerializable(BUNDLE_KEY_MOVIE_INFO, movieInfo)
            }
        }
    }
}