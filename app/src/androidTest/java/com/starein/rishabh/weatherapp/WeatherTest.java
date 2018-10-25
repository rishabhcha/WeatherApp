package com.starein.rishabh.weatherapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.starein.rishabh.weatherapp.ui.weather.WeatherActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class WeatherTest {

    @Rule
    public ActivityTestRule<WeatherActivity> mActivityRule =
            new ActivityTestRule<>(WeatherActivity.class);

    @Test
    public void isProgressBarVisible(){
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()));
    }

    @Test
    public void isUIModifiedAfterAPI(){

        CountingIdlingResource mainActivityIdlingResource = mActivityRule.getActivity().getEspressoIdlingResourceForMainActivity();
        Espresso.registerIdlingResources(mainActivityIdlingResource);

        //check if default text changed after api call
        onView(withId(R.id.currTempTextView)).check(matches(not(withText(R.string._20))));
        onView(withId(R.id.cityTextView)).check(matches(not(withText(R.string.city_name))));

        //check if recyclerview became visible
        onView(withId(R.id.weatherRecyclerView)).check(matches(isDisplayed()));

        Espresso.unregisterIdlingResources(mainActivityIdlingResource);
    }





}
