package com.f0rgiv.taskmaster.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.f0rgiv.taskmaster.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

  @Rule
  public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

  @Test
  public void mainActivityTest2() {
    ViewInteraction materialButton = onView(
      allOf(withId(R.id.addTaskButton), withText("Add Task"),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          0),
        isDisplayed()));
    materialButton.perform(click());

    ViewInteraction appCompatEditText = onView(
      allOf(withId(R.id.editTextNewTaskTitle),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          3),
        isDisplayed()));
    appCompatEditText.perform(replaceText("test taask"), closeSoftKeyboard());

    ViewInteraction appCompatEditText2 = onView(
      allOf(withId(R.id.editTextNewTeskDescription),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          4),
        isDisplayed()));
    appCompatEditText2.perform(replaceText("this is a test"), closeSoftKeyboard());

    ViewInteraction appCompatEditText3 = onView(
      allOf(withId(R.id.editTextNewTeskDescription), withText("this is a test"),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          4),
        isDisplayed()));
    appCompatEditText3.perform(pressImeActionButton());

    ViewInteraction materialButton2 = onView(
      allOf(withId(R.id.createNewTask), withText("Add Task"),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          1),
        isDisplayed()));
    materialButton2.perform(click());

    pressBack();

    ViewInteraction materialButton3 = onView(
      allOf(withId(R.id.taskFragmentTaskButton), withText("test taask"),
        childAtPosition(
          childAtPosition(
            withId(R.id.mainRecyclerView),
            0),
          0),
        isDisplayed()));
    materialButton3.perform(click());

    ViewInteraction textView = onView(
      allOf(withId(R.id.taskDetailTitle), withText("test taask"),
        withParent(withParent(withId(android.R.id.content))),
        isDisplayed()));
    textView.check(matches(withText("test taask")));

    pressBack();

    ViewInteraction overflowMenuButton = onView(
      allOf(withContentDescription("More options"),
        childAtPosition(
          childAtPosition(
            withId(R.id.action_bar),
            1),
          0),
        isDisplayed()));
    overflowMenuButton.perform(click());

    ViewInteraction materialTextView = onView(
      allOf(withId(R.id.title), withText("Settings"),
        childAtPosition(
          childAtPosition(
            withId(R.id.content),
            0),
          0),
        isDisplayed()));
    materialTextView.perform(click());

    ViewInteraction appCompatEditText4 = onView(
      allOf(withId(R.id.editTextUsername),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          1),
        isDisplayed()));
    appCompatEditText4.perform(replaceText("testuser1"), closeSoftKeyboard());

    ViewInteraction appCompatEditText5 = onView(
      allOf(withId(R.id.editTextUsername), withText("testuser1"),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          1),
        isDisplayed()));
    appCompatEditText5.perform(pressImeActionButton());

    ViewInteraction materialButton4 = onView(
      allOf(withId(R.id.saveSettingsButton), withText("Save"),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          2),
        isDisplayed()));
    materialButton4.perform(click());

    pressBack();

    ViewInteraction textView2 = onView(
      allOf(withId(R.id.mainTasksGreetingLabel), withText("testuser1's tasks"),
        withParent(withParent(withId(android.R.id.content))),
        isDisplayed()));
    textView2.check(matches(withText("testuser1's tasks")));
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
