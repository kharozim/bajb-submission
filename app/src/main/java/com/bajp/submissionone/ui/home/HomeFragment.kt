package com.bajp.submissionone.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bajp.submissionone.data.entities.ContentItemEntity
import com.bajp.submissionone.databinding.FragmentHomeBinding
import com.bajp.submissionone.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
//    private val homeViewModel: HomeViewModel by viewModels()

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
        activity().homeViewModel.getListMovie()
        activity().homeViewModel.listMovie.observe(viewLifecycleOwner, {
            showData(it.results, true)
        })
    }

    private fun setupTv() {
        activity().homeViewModel.getListTV()
        activity().homeViewModel.listTV.observe(viewLifecycleOwner, {
            showData(it.results, false)
        })
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

    private fun activity() = requireActivity() as HomeActivity

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