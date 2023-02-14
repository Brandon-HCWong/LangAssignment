package com.brandonwong.langassignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.brandonwong.langassignment.R
import com.brandonwong.langassignment.data.`object`.MovieInfo
import com.brandonwong.langassignment.databinding.FragmentChartBinding
import com.brandonwong.langassignment.ui.recyclerview.adapter.FooterAdapter
import com.brandonwong.langassignment.ui.recyclerview.adapter.MovieAdapter
import com.brandonwong.langassignment.ui.viewmodel.ChartViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChartFragment : Fragment() {
    private val viewModel: ChartViewModel by viewModel()
    private var binding: FragmentChartBinding? = null
    private lateinit var movieAdapter : MovieAdapter
    private lateinit var footerAdapter : FooterAdapter
    private var favoriteMoveSyncJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return with(FragmentChartBinding.inflate(inflater, container, false)) {
            binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
        initData()
    }

    private fun initToolbar() {
        binding?.run {
            toolbar.title = getString(R.string.chart_toolbar_title)
        }
    }

    private fun initRecyclerView() {
        binding?.run {
            movieAdapter = MovieAdapter(itemInteractionListener = object : MovieAdapter.InteractionListener {
                override fun onMovieItemClick(movieInfo: MovieInfo) {
                    Log.d("BrandonDebug", "[ChartFragment - onMovieItemClick] : movieInfo = $movieInfo")
                    NavHostFragment
                        .findNavController(this@ChartFragment)
                        .navigate(
                            R.id.action_ChartFragment_to_DetailFragment,
                            DetailFragment.generateArguments(movieInfo)
                        )
                }

                override fun onFavoriteButtonClick(
                    movieInfo: MovieInfo,
                    position: Int,
                    isFavorite: Boolean
                ) {
                    Log.d("BrandonDebug", "[ChartFragment - onFavoriteButtonClick] : movieInfo = $movieInfo, position = $position, isFavorite = $isFavorite")
                    if (favoriteMoveSyncJob?.isActive == true || movieInfo.isFavorite == isFavorite) {
                        Log.d("BrandonDebug", "[ChartFragment - onFavoriteButtonClick] : favoriteMoveSyncJob is running.")
                        return
                    }
                    favoriteMoveSyncJob = lifecycleScope.launch {
                        try {
                            val syncResult = viewModel.syncFavoriteMovie(movieInfo.id, isFavorite)
                            Log.d(
                                "BrandonDebug",
                                "[ChartFragment - onFavoriteButtonClick] : syncResult = $syncResult"
                            )
                            when (syncResult.isSuccess) {
                                true -> {
                                    movieInfo.isFavorite = isFavorite
                                    movieAdapter.notifyItemChanged(position)
                                }
                                false -> {
                                    Toast.makeText(
                                        requireContext(),
                                        getString(R.string.chart_sync_favorite_failed),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.chart_sync_favorite_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
            rvGallery.layoutManager = LinearLayoutManager(requireContext())
            rvGallery.adapter = movieAdapter.withLoadStateFooter(FooterAdapter(object : FooterAdapter.InteractionListener {
                override fun onRetry() {
                    movieAdapter.retry()
                }

                override fun getItemCount(): Int = movieAdapter.itemCount
            }).apply {
                footerAdapter = this
            })
        }
    }

    private fun initData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.rowPagingDataFlow.collect {
                    Log.d("BrandonDebug", "[ChartFragment - initData] : rowPagingDataFlow = $it")
                    movieAdapter.submitData(it)
                }
            }
        }
    }
}