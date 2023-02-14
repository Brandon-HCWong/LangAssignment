package com.brandonwong.langassignment.ui.recyclerview.viewholder

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.brandonwong.langassignment.R
import com.brandonwong.langassignment.databinding.ItemFooterBinding
import com.brandonwong.langassignment.ui.recyclerview.adapter.FooterAdapter

class FooterViewHolder(
    private val binding: ItemFooterBinding,
    private val interactionListener: FooterAdapter.InteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(loadState: LoadState, isItemsEmpty: Boolean) {
        with(binding) {
            tvStatus.text = when (loadState) {
                is LoadState.NotLoading -> {
                    when {
                        isItemsEmpty -> root.context.getString(R.string.chart_footer_empty)
                        loadState.endOfPaginationReached -> root.context.getString(R.string.chart_footer_reach_the_limit)
                        else -> ""
                    }
                }
                is LoadState.Error -> {
                    root.context.getString(R.string.chart_footer_loading_error)
                }
                else -> ""
            }
            buttonRetry.isVisible = loadState is LoadState.Error
            buttonRetry.setOnClickListener {
                interactionListener.onRetry()
            }
        }
    }
}