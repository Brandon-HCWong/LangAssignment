package com.brandonwong.langassignment.ui.recyclerview.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.brandonwong.langassignment.R
import com.brandonwong.langassignment.data.`object`.MovieInfo
import com.brandonwong.langassignment.data.provider.ApiInfoProvider
import com.brandonwong.langassignment.databinding.ItemMovieBinding
import com.brandonwong.langassignment.ui.recyclerview.adapter.MovieAdapter
import com.bumptech.glide.Glide

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val itemInteractionListener: MovieAdapter.InteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(movieInfo: MovieInfo, position: Int) {
        Log.d("BrandonDebug", "[MovieViewHolder - onBind] : position = $position, movieInfo = [${movieInfo.hashCode()} : $movieInfo]")
        with(binding) {
            tvTitleContent.text = movieInfo.originalTitle
            Glide.with(root)
                .load("${ApiInfoProvider.geTmdbImageBaseUrl()}${movieInfo.posterPath}")
                .placeholder(R.drawable.ic_photo_placeholder)
                .into(ivPoster)
            when (movieInfo.isFavorite) {
                true -> {
                    ibFavourite.setImageResource(R.drawable.ic_favourite)
                }
                false -> {
                    ibFavourite.setImageResource(R.drawable.ic_unfavourite)
                }
            }
            ibFavourite.setOnClickListener {
                itemInteractionListener.onFavoriteButtonClick(movieInfo, position, !movieInfo.isFavorite)
            }
            root.setOnClickListener {
                itemInteractionListener.onMovieItemClick(movieInfo)
            }
        }
    }

}