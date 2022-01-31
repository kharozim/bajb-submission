package com.bajp.submissionthree.ui.home.content

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.databinding.FragmentHomeBinding
import com.bajp.submissionthree.utils.ViewModelFactory
import com.bajp.submissionthree.ui.detail.DetailActivity
import com.bajp.submissionthree.ui.home.HomeViewModel
import com.bajp.submissionthree.ui.home.ItemClick
import com.bajp.submissionthree.vo.Status
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    private lateinit var sharedViewModel: HomeViewModel

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private var isMovie = false

    @Inject
    lateinit var factory : ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isMovie =
            savedInstanceState?.getBoolean(IS_MOVIE, false) ?: (arguments?.getBoolean(IS_MOVIE)
                ?: false)
        return binding.root
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_MOVIE, isMovie)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel =
            ViewModelProvider(requireActivity(), factory = factory)[HomeViewModel::class.java]
        if (isMovie) {
            setupMovie()
        } else {
            setupTv()
        }
    }

    private fun setupMovie() {
        showLoading(true)
        sharedViewModel.getListMovie().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    showLoading(true)
                }
                Status.ERROR -> {
                    showLoading(false)
                }
                Status.SUCCESS -> {
                    it.data?.let { data -> showData(data, true) }
                    showLoading(false)
                }
            }

        })
    }

    private fun setupTv() {
        showLoading(true)
        sharedViewModel.getListTV().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    showLoading(true)
                }
                Status.ERROR -> {
                    showLoading(false)
                }
                Status.SUCCESS -> {
                    it.data?.let {data-> showData(data, false) }
                    showLoading(false)
                }
            }
        })
    }

    private fun showData(data: PagedList<CatalogEntity>, isMovie: Boolean) {
        val adapter = HomeContentAdapter()
        adapter.clickItem(object : ItemClick {
            override fun onItemClick(data: Any?, position: Int) {
                data as CatalogEntity
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DETAIL_IS_MOVIE, isMovie)
                intent.putExtra(DetailActivity.EXTRA_DETAIL_ID, data.id)
                startActivity(intent)
            }
        })
        adapter.submitList(data)
        Log.d("TAG", "showData: ${adapter.itemCount}")
        adapter.notifyDataSetChanged()
        binding.rvContent.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressFragment.isVisible = isLoading
        binding.rvContent.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }


    companion object {
        const val IS_MOVIE = "IS_MOVIE"
        fun onSaveInstance(isMovie: Boolean): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            bundle.putBoolean(IS_MOVIE, isMovie)
            fragment.arguments = bundle
            return fragment
        }
    }
}