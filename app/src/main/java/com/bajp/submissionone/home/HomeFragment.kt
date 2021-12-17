package com.bajp.submissionone.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bajp.submissionone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
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