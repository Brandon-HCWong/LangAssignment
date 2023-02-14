package com.brandonwong.langassignment.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.brandonwong.langassignment.data.`object`.MovieInfo
import com.brandonwong.langassignment.databinding.ItemMovieBinding
import com.brandonwong.langassignment.ui.recyclerview.viewholder.MovieViewHolder

class MovieAdapter(
    private val itemInteractionListener: InteractionListener
) : PagingDataAdapter<MovieInfo, MovieViewHolder>(diffCallback)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemInteractionListener
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.also {
            holder.onBind(it, position)
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MovieInfo>() {
            override fun areItemsTheSame(
                oldItem: MovieInfo,
                newItem: MovieInfo
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MovieInfo,
                newItem: MovieInfo
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    interface InteractionListener {
        fun onMovieItemClick(movieInfo: MovieInfo)
        fun onFavoriteButtonClick(movieInfo: MovieInfo, position: Int, isFavorite: Boolean)
    }
}