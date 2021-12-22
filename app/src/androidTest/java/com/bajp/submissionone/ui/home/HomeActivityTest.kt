package com.bajp.submissionone.ui.home

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.bajp.submissionone.R
import com.bajp.submissionone.utils.DataUtil
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    private val dummyMovie = DataUtil.generateDataMovie()
    private val dummyTv = DataUtil.generateDataTV()

    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun openTvShow() {
        onView(withText("TV SHOW"))
            .check(matches(isDisplayed()))
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rvContent)).check(matches(isDisplayed()))
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.scrollToPosition<HomeContentAdapter.MyViewHolder>(
                7
            )
        )
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HomeContentAdapter.MyViewHolder>(
                7, click()
            )
        )
        onView(withId(R.id.tvName)).check(matches(withText(dummyTv.results[7].name)))
    }

    @Test
    fun openMovie() {
        onView(withText("MOVIE"))
            .check(matches(isDisplayed()))
        onView(withText("MOVIE")).perform(click())
        onView(withId(R.id.rvContent)).check(matches(isDisplayed()))
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.scrollToPosition<HomeContentAdapter.MyViewHolder>(
                5
            )
        )
        onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<HomeContentAdapter.MyViewHolder>(
                5, click()
            )
        )
        onView(withId(R.id.tvName)).check(matches(withText(dummyMovie.results[5].name)))
        onView(withId(R.id.ivPoster)).perform(click())
        delayMiliSecond(1000)
        onView(withId(R.id.ivDialogPoster)).check(matches(isDisplayed()))
    }

    private fun delayMiliSecond(milis: Long = 2000) {
        try {
            Thread.sleep(milis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}