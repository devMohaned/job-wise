package com.job.wise.ui

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.navigation.NavigationView
import com.job.wise.MainActivity
import org.junit.Test
import org.junit.runner.RunWith
import com.job.wise.R


@RunWith(AndroidJUnit4::class)
class NavigationTests {
    @Test
    fun testNavigationToProjects() {
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        // Create a graphical FragmentScenario for the TitleScreen
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
    //    val titleScenario = launchFragmentInContainer<ProjectsFragment>()
        activityScenario.onActivity { activity ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(activity.findViewById<NavigationView>(R.id.nav_view), navController)
        }

        // Verify that performing a click changes the NavController’s state
        onView(ViewMatchers.withContentDescription(androidx.navigation.ui.R.string.nav_app_bar_open_drawer_description)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.projectsFragment)).perform(ViewActions.click())
      //  Assert.assertEquals(navController.currentDestination?.id,R.id.projectsFragment)
        onView(withId(R.id.addNewProjectButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}