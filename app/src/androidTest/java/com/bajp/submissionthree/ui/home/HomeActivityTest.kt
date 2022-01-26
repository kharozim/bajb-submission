package com.bajp.submissionthree.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.bajp.submissionthree.R
import com.bajp.submissionthree.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }


    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)


    private fun openTvShow() {
        onView(withText("TV SHOW"))
            .check(matches(isDisplayed()))
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rvContent)).check(matches(isDisplayed()))
    }

    private fun openMovie() {
        onView(withText("MOVIE"))
            .check(matches(isDisplayed()))
        onView(withText("MOVIE")).perform(click())
        onView(withId(R.id.rvContent)).check(matches(isDisplayed()))
    }

    @Test
    fun detailTvShow() {
        val position = 0
        openTvShow()
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HomeContentAdapter.MyViewHolder>(
                position, click()
            )
        )
        onView(withId(R.id.ivShare)).check(matches(isDisplayed()))
        onView(withId(R.id.tvName)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDescription)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRelease)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRating)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPoster)).perform(click())
        onView(withId(R.id.ivDialogPoster)).check(matches(isDisplayed()))
    }

    @Test
    fun sliderTv() {
        openTvShow()
        onView(withId(R.id.viewPagerSlider)).check(matches(isDisplayed()))
        onView(withId(R.id.viewPagerSlider)).perform(swipeLeft())
    }

    @Test
    fun detailMovie() {
        val position = 0
        openMovie()
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HomeContentAdapter.MyViewHolder>(
                position, click()
            )
        )
        onView(withId(R.id.ivShare)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.tvName)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRelease)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRating)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDescription)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPoster)).perform(click())
        onView(withId(R.id.ivDialogPoster)).check(matches(isDisplayed()))
    }


    @Test
    fun sliderMovie() {
        openMovie()
        onView(withId(R.id.viewPagerSlider)).check(matches(isDisplayed()))
        onView(withId(R.id.viewPagerSlider)).perform(swipeLeft())
    }
}