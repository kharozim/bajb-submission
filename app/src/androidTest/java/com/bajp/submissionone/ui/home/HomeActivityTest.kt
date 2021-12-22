package com.bajp.submissionone.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.bajp.submissionone.R
import com.bajp.submissionone.utils.DataUtil
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    private val dummyMovie = DataUtil.generateDataMovie()
    private val dummyTv = DataUtil.generateDataTV()

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun detailTvShow() {
        onView(withText("TV SHOW"))
            .check(matches(isDisplayed()))
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rvContent)).check(matches(isDisplayed()))
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.scrollToPosition<HomeContentAdapter.MyViewHolder>(
                dummyTv.results.size
            )
        )
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HomeContentAdapter.MyViewHolder>(
                3, click()
            )
        )
        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.tvName)).check(matches(withText(dummyTv.results[3].name)))
        onView(withId(R.id.ivPoster)).perform(click())
        onView(withId(R.id.ivDialogPoster)).check(matches(isDisplayed()))
    }

    @Test
    fun shareTvShow() {
        onView(withText("TV SHOW"))
            .check(matches(isDisplayed()))
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rvContent)).check(matches(isDisplayed()))
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.scrollToPosition<HomeContentAdapter.MyViewHolder>(
                0
            )
        )
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HomeContentAdapter.MyViewHolder>(
                0, click()
            )
        )
        onView(withId(R.id.ivShare)).check(matches(isDisplayed()))
        onView(withId(R.id.ivShare)).perform(click())
    }

    @Test
    fun openMovie() {
        onView(withText("MOVIE"))
            .check(matches(isDisplayed()))
        onView(withText("MOVIE")).perform(click())
        onView(withId(R.id.rvContent)).check(matches(isDisplayed()))
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.scrollToPosition<HomeContentAdapter.MyViewHolder>(
                dummyMovie.results.size
            )
        )
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HomeContentAdapter.MyViewHolder>(
                2, click()
            )
        )
        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.tvName)).check(matches(withText(dummyMovie.results[2].name)))
        onView(withId(R.id.ivPoster)).perform(click())
        onView(withId(R.id.ivDialogPoster)).check(matches(isDisplayed()))
    }

    @Test
    fun shareMovie() {
        onView(withText("MOVIE"))
            .check(matches(isDisplayed()))
        onView(withText("MOVIE")).perform(click())
        onView(withId(R.id.rvContent)).check(matches(isDisplayed()))
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.scrollToPosition<HomeContentAdapter.MyViewHolder>(
                dummyMovie.results.size
            )
        )
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HomeContentAdapter.MyViewHolder>(
                5, click()
            )
        )
        onView(withId(R.id.ivShare)).check(matches(isDisplayed()))
        onView(withId(R.id.ivShare)).perform(click())
    }
}