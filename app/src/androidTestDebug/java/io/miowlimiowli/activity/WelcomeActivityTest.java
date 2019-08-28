package io.miowlimiowli.activity;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.miowlimiowli.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WelcomeActivityTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    @Test
    public void welcomeActivityTest() {
        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.sign_up_button),
                        childAtPosition(
                                allOf(withId(R.id.view_constraint_layout),
                                        childAtPosition(
                                                withId(R.id.welcome_constraint_layout),
                                                3)),
                                0),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.signup_nickname_edit_text),
                        childAtPosition(
                                allOf(withId(R.id.sign_up_fields_constraint_layout),
                                        childAtPosition(
                                                withId(R.id.signup_constraint_layout),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("qwq"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.signup_nickname_edit_text), withText("qwq"),
                        childAtPosition(
                                allOf(withId(R.id.sign_up_fields_constraint_layout),
                                        childAtPosition(
                                                withId(R.id.signup_constraint_layout),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.signup_mail_edit_text),
                        childAtPosition(
                                allOf(withId(R.id.sign_up_fields_constraint_layout),
                                        childAtPosition(
                                                withId(R.id.signup_constraint_layout),
                                                2)),
                                2),
                        isDisplayed()));
        appCompatEditText3.perform(pressImeActionButton());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.signup_password_edit_text),
                        childAtPosition(
                                allOf(withId(R.id.sign_up_fields_constraint_layout),
                                        childAtPosition(
                                                withId(R.id.signup_constraint_layout),
                                                2)),
                                4),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("qwq"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.signup_password_edit_text), withText("qwq"),
                        childAtPosition(
                                allOf(withId(R.id.sign_up_fields_constraint_layout),
                                        childAtPosition(
                                                withId(R.id.signup_constraint_layout),
                                                2)),
                                4),
                        isDisplayed()));
        appCompatEditText5.perform(pressImeActionButton());

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.agree_switch),
                        childAtPosition(
                                allOf(withId(R.id.tos_constraint_layout),
                                        childAtPosition(
                                                withId(R.id.signup_constraint_layout),
                                                3)),
                                1),
                        isDisplayed()));
        switch_.perform(click());

        ViewInteraction linearLayout2 = onView(
                allOf(withId(R.id.sign_up_button),
                        childAtPosition(
                                allOf(withId(R.id.signup_constraint_layout),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        linearLayout2.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
