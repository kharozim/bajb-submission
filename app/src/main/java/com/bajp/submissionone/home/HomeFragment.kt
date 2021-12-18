package com.bajp.submissionone.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bajp.submissionone.data.entities.ContentItemEntity
import com.bajp.submissionone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val homeViewModel: HomeViewModel by viewModels()

    private var isMovie = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
        if (isMovie) {
            setupMovie()
        } else {
            setupTv()
        }
    }

    private fun setupMovie() {
        homeViewModel.getListMovie(requireContext())
        homeViewModel.listMovie.observe(viewLifecycleOwner, {
            showData(it.results)
        })
    }

    private fun setupTv() {
        homeViewModel.getListTV(requireContext())
        homeViewModel.listTV.observe(viewLifecycleOwner, {
            showData(it.results)
        })
    }

    private fun showData(results: List<ContentItemEntity>) {
        val adapter = HomeContentAdapter()
        adapter.clickItem(object : HomeContentAdapter.HomeContentClick {
            override fun onContentClick(data: Any?, position: Int) {
                data as ContentItemEntity
                Toast.makeText(requireContext(), data.name, Toast.LENGTH_SHORT).show()
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