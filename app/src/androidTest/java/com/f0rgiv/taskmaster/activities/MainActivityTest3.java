package com.f0rgiv.taskmaster.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
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

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest3 {

  @Rule
  public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

  @Test
  public void mainActivityTest3() {
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
    appCompatEditText.perform(replaceText("go it"), closeSoftKeyboard());

    ViewInteraction appCompatEditText2 = onView(
      allOf(withId(R.id.editTextNewTeskDescription),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          4),
        isDisplayed()));
    appCompatEditText2.perform(replaceText("fgh I h to"), closeSoftKeyboard());

    ViewInteraction appCompatEditText3 = onView(
      allOf(withId(R.id.editTextNewTeskDescription), withText("fgh I h to"),
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

    ViewInteraction appCompatSpinner = onView(
      allOf(withId(R.id.teamSpinner),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          5),
        isDisplayed()));
    appCompatSpinner.perform(click());

    DataInteraction checkedTextView = onData(anything())
      .inAdapterView(childAtPosition(
        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
        0))
      .atPosition(2);
    checkedTextView.perform(click());

    ViewInteraction appCompatEditText4 = onView(
      allOf(withId(R.id.editTextUsername),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          1),
        isDisplayed()));
    appCompatEditText4.perform(replaceText("Jerry"), closeSoftKeyboard());

    ViewInteraction appCompatEditText5 = onView(
      allOf(withId(R.id.editTextUsername), withText("Jerry"),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          1),
        isDisplayed()));
    appCompatEditText5.perform(pressImeActionButton());

    ViewInteraction materialButton3 = onView(
      allOf(withId(R.id.saveSettingsButton), withText("Save"),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          2),
        isDisplayed()));
    materialButton3.perform(click());

    pressBack();

    ViewInteraction materialButton4 = onView(
      allOf(withId(R.id.addTaskButton), withText("Add Task"),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          0),
        isDisplayed()));
    materialButton4.perform(click());

    ViewInteraction appCompatEditText6 = onView(
      allOf(withId(R.id.editTextNewTaskTitle),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          3),
        isDisplayed()));
    appCompatEditText6.perform(replaceText("task"), closeSoftKeyboard());

    ViewInteraction appCompatEditText7 = onView(
      allOf(withId(R.id.editTextNewTeskDescription),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          4),
        isDisplayed()));
    appCompatEditText7.perform(replaceText("task detail"), closeSoftKeyboard());

    ViewInteraction appCompatEditText8 = onView(
      allOf(withId(R.id.editTextNewTeskDescription), withText("task detail"),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          4),
        isDisplayed()));
    appCompatEditText8.perform(pressImeActionButton());

    ViewInteraction materialButton5 = onView(
      allOf(withId(R.id.createNewTask), withText("Add Task"),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          1),
        isDisplayed()));
    materialButton5.perform(click());

    pressBack();

    ViewInteraction materialButton6 = onView(
      allOf(withId(R.id.addTaskButton), withText("Add Task"),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          0),
        isDisplayed()));
    materialButton6.perform(click());

    ViewInteraction appCompatSpinner2 = onView(
      allOf(withId(R.id.addTaskTeamSpinner),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          8),
        isDisplayed()));
    appCompatSpinner2.perform(click());

    DataInteraction checkedTextView2 = onData(anything())
      .inAdapterView(childAtPosition(
        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
        0))
      .atPosition(2);
    checkedTextView2.perform(click());

    ViewInteraction appCompatEditText9 = onView(
      allOf(withId(R.id.editTextNewTaskTitle),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          3),
        isDisplayed()));
    appCompatEditText9.perform(replaceText("team two task"), closeSoftKeyboard());

    ViewInteraction appCompatEditText10 = onView(
      allOf(withId(R.id.editTextNewTeskDescription),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          4),
        isDisplayed()));
    appCompatEditText10.perform(replaceText("thanks for the invite \uD83D\uDE0A\uD83D\uDE0A\uD83D\uDE0A\uD83D\uDC4D"), closeSoftKeyboard());

    ViewInteraction appCompatEditText11 = onView(
      allOf(withId(R.id.editTextNewTeskDescription), withText("thanks for the invite \uD83D\uDE0A\uD83D\uDE0A\uD83D\uDE0A\uD83D\uDC4D"),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          4),
        isDisplayed()));
    appCompatEditText11.perform(pressImeActionButton());

    ViewInteraction materialButton7 = onView(
      allOf(withId(R.id.createNewTask), withText("Add Task"),
        childAtPosition(
          allOf(withId(R.id.addTaskButton),
            childAtPosition(
              withId(android.R.id.content),
              0)),
          1),
        isDisplayed()));
    materialButton7.perform(click());

    pressBack();

    ViewInteraction materialButton8 = onView(
      allOf(withId(R.id.taskFragmentTaskButton), withText("team two task"),
        childAtPosition(
          childAtPosition(
            withId(R.id.mainRecyclerView),
            0),
          0),
        isDisplayed()));
    materialButton8.perform(click());

    pressBack();
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
