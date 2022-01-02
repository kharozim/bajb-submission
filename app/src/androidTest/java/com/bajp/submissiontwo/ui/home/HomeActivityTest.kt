package com.bajp.submissiontwo.ui.home

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
import com.bajp.submissiontwo.R
import com.bajp.submissiontwo.utils.EspressoIdlingResource
import com.bajp.submissiontwo.utils.FormatUtil
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

//    private val dummyMovie = DataUtil.generateDataMovie()
//    private val dummyTv = DataUtil.generateDataTV()

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
//        onView(withId(R.id.rvContent)).perform(
//            RecyclerViewActions.scrollToPosition<HomeContentAdapter.MyViewHolder>(
//                dummyTv.results.size
//            )
//        )
    }

    private fun openMovie() {
        onView(withText("MOVIE"))
            .check(matches(isDisplayed()))
        onView(withText("MOVIE")).perform(click())
        onView(withId(R.id.rvContent)).check(matches(isDisplayed()))
//        onView(withId(R.id.rvContent)).perform(
//            RecyclerViewActions.scrollToPosition<HomeContentAdapter.MyViewHolder>(
//            )
//        )
    }

    @Test
    fun detailTvShow() {
        val position = 0
//        val dummy = dummyTv.results[position]
//        val rating = dummy.rating
//        val ratingCount = dummy.ratingCount
//        val dateRelease = FormatUtil.formatDate(dummy.releaseDate)

        openTvShow()
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HomeContentAdapter.MyViewHolder>(
                position, click()
            )
        )
        onView(withId(R.id.ivShare)).check(matches(isDisplayed()))
//        onView(withId(R.id.tvName)).check(matches(withText(dummy.name)))
//        onView(withId(R.id.tvDescription)).check(matches(withText(dummy.description)))
//        onView(withId(R.id.tvRelease)).check(matches(withText(dateRelease)))
//        onView(withId(R.id.tvRating)).check(matches(withText("$rating / 10 from $ratingCount Users")))
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
//        val dummy = dummyMovie.results[position]
//        val rating = dummy.rating
//        val ratingCount = dummy.ratingCount
//        val dateRelease = FormatUtil.formatDate(dummy.releaseDate)

        openMovie()
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HomeContentAdapter.MyViewHolder>(
                position, click()
            )
        )
        onView(withId(R.id.ivShare)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
//        onView(withId(R.id.tvName)).check(matches(withText(dummy.name)))
//        onView(withId(R.id.tvRelease)).check(matches(withText(dateRelease)))
//        onView(withId(R.id.tvRating)).check(matches(withText("$rating / 10 from $ratingCount Users")))
//        onView(withId(R.id.tvDescription)).check(matches(withText(dummy.description)))
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