package com.bajp.submissiontwo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bajp.submissiontwo.data.entities.ContentItemEntity
import com.bajp.submissiontwo.databinding.FragmentHomeBinding
import com.bajp.submissiontwo.ui.ViewModelFactory
import com.bajp.submissiontwo.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private lateinit var sharedViewModel: HomeViewModel

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private var isMovie = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        val factory = ViewModelFactory.getInstance()
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
        sharedViewModel.getListMovie().observe(viewLifecycleOwner, { data ->
            data?.results?.let { showData(it, true) }
            showLoading(false)
        })
    }

    private fun setupTv() {
        showLoading(true)
        sharedViewModel.getListTV().observe(viewLifecycleOwner, { data ->
            data?.results?.let { showData(it, false) }
            showLoading(false)
        })

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressFragment.isVisible = isLoading
        binding.rvContent.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    private fun showData(results: List<ContentItemEntity>, isMovie: Boolean) {
        val adapter = HomeContentAdapter()
        adapter.clickItem(object : ItemClick {
            override fun onItemClick(data: Any?, position: Int) {
                data as ContentItemEntity
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DETAIL_IS_MOVIE, isMovie)
                intent.putExtra(DetailActivity.EXTRA_DETAIL_ID, data.id)
                startActivity(intent)
            }
        })
        adapter.setItems(results)
        binding.rvContent.adapter = adapter
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