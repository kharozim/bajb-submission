package com.bajp.submissionthree.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bajp.submissionthree.R
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.databinding.ActivityFavoriteBinding
import com.bajp.submissionthree.ui.detail.DetailActivity
import com.bajp.submissionthree.ui.home.ItemClick
import com.bajp.submissionthree.utils.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class FavoriteActivity : DaggerAppCompatActivity() {

    private val binding by lazy { ActivityFavoriteBinding.inflate(layoutInflater) }
    private lateinit var viewModel: FavoriteViewModel
    private val adapter by lazy { FavoriteAdapter() }
    private val listFavorite = ArrayList<CatalogEntity>()
    private val listFilter = ArrayList<CatalogEntity>()


    @Inject
    lateinit var factory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
        setObserver()
    }

    private fun setObserver() {
        viewModel.getFavorite().observe(this) { result ->
            if (!result.isNullOrEmpty()) {
                showData(result)
                setEmpty(false)
            } else {
                setEmpty(true)
            }
        }
    }

    private fun setEmpty(isEmpty: Boolean) {
        binding.run {
            lottieEmpty.isVisible = isEmpty
            tvEmptyBody.isVisible = isEmpty
            tvEmptySubBody.isVisible = isEmpty
            rvFavorite.isVisible = !isEmpty
        }
    }

    private fun showData(result: List<CatalogEntity>) {
        listFavorite.clear()
        adapter.onClickItem(object : ItemClick {
            override fun onItemClick(data: Any?, position: Int) {
                data as CatalogEntity
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DETAIL_IS_MOVIE, data.isMovie)
                intent.putExtra(DetailActivity.EXTRA_DETAIL_ID, data.id)
                startActivity(intent)
            }

        })
        listFavorite.addAll(result)
        doSearch("")
        binding.rvFavorite.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search,menu)
        val searchView  = menu.findItem(R.id.menuSearch)?.actionView as SearchView
        searchView.apply {
            isIconified = false
            isFocusable = true
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("TAG", "onQueryTextChange: $newText")
                doSearch(newText?:"")
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun doSearch(keySearch: String) {
        listFilter.clear()
        val filter = listFavorite.asSequence().filter {
            it.name.contains(keySearch,true)
        }.toList()
        listFilter.addAll(filter)
        Log.d("TAG", "onQueryTextChange list filter : ${filter.size}")
        adapter.setData(listFilter)
    }
}