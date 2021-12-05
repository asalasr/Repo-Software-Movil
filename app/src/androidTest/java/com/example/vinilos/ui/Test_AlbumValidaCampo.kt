package com.example.vinilos.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.vinilos.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class Test_AlbumValidaCampo {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test_AlbumValidaCampo() {
        val materialButton = onView(
            allOf(
                withId(R.id.button_collector_rol), withText("COLECCIONISTA"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    1
                )
            )
        )
        materialButton.perform(scrollTo(), click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val materialButton2 = onView(
            allOf(
                withId(R.id.button_albums_menu), withText("ÁLBUMES"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.RelativeLayout")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val floatingActionButton = onView(
            allOf(
                withId(R.id.fab_albums), withContentDescription("Crear álbum"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val textView = onView(
            allOf(
                withId(R.id.textViewName), withText("Nombre"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Nombre")))

        val textView2 = onView(
            allOf(
                withId(R.id.textViewCover), withText("Cover"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Cover")))

        val textView3 = onView(
            allOf(
                withId(R.id.textViewReleaseDate), withText("Fecha lanzamiento"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Fecha lanzamiento")))

        val textView4 = onView(
            allOf(
                withId(R.id.textViewGenre), withText("Genero"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Genero")))

        val textView5 = onView(
            allOf(
                withId(R.id.textViewRecordLabel), withText("Disquera"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.View::class.java))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Disquera")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
