package com.bajp.submissionone.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.bajp.submissionone.R
import com.bajp.submissionone.utils.DataUtil
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class HomeFragmentTest{
    private val dummyMovie = DataUtil.generateDataMovie().results

    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun runFirst(){
        delayTwoSecond()
//        onView(withId(R.id.tvHome)).check(matches(ViewMatchers.withText("ini test")))
//
//        onView(withId(R.id.viewPagerContent))
//            .check(matches(isDisplayed()))
//        onView(withId(R.id.rvContent))
//            .check(matches(isDisplayed()))
        onView(withId(R.id.rvContent))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size)).perform(
                ViewActions.click()
            )
    }

    @Test
    fun runSecond(){
        delayTwoSecond()
        onView(ViewMatchers.withId(R.id.rvContent)).check(matches(isDisplayed()))


    }

    private fun delayTwoSecond() {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}