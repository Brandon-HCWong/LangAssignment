package com.brandonwong.langassignment.ui.recyclerview.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.brandonwong.langassignment.databinding.ItemFooterBinding
import com.brandonwong.langassignment.ui.recyclerview.viewholder.FooterViewHolder

class FooterAdapter(
    private val interactionListener: InteractionListener
) : LoadStateAdapter<FooterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        Log.d("BrandonDebug", "[FooterAdapter - onCreateViewHolder] : loadState = $loadState")
        return FooterViewHolder(
            ItemFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            interactionListener
        )
    }

    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.onBind(loadState, interactionListener.getItemCount() < 1)
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        Log.d("BrandonDebug", "[FooterAdapter - displayLoadStateAsItem] : loadState = $loadState")
        return loadState is LoadState.Error || loadState is LoadState.NotLoading
    }

    interface InteractionListener {
        fun onRetry()
        fun getItemCount(): Int
    }
}