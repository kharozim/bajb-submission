package com.bajp.submissionthree.di

import com.bajp.submissionthree.di.home.HomeFragmentBuildersModule
import com.bajp.submissionthree.ui.detail.DetailActivity
import com.bajp.submissionthree.ui.favorite.FavoriteActivity
import com.bajp.submissionthree.ui.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [HomeFragmentBuildersModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): DetailActivity

    @ContributesAndroidInjector
    abstract fun contributeFavoriteActivity(): FavoriteActivity

}