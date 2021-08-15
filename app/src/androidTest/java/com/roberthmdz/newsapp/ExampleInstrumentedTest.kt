package com.roberthmdz.newsapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    // Create 2 rules
    // Rule 1 to test Hilt
    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    // Rule 2 to test jetpack compose
    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun ItemsAddedToScreen() {
        composeTestRule.setContent {
            NewsListScreen(navController = rememberNavController())
        }
        composeTestRule.onNodeWithText("Title 1").assertExists()
        composeTestRule.onNodeWithText("Title 2").assertExists()

    }
}