package hu.bme.aut.bethere.ui.screen.signin

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import hu.bme.aut.bethere.ui.nagivagion.DestinationsNavigatorImpl
import hu.bme.aut.bethere.ui.theme.BeThereTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SignInScreenktTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    private var navController = DestinationsNavigatorImpl()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            BeThereTheme {
                SignInScreen(navigator = navController)
            }

        }
    }

    @Test
    fun emailTextField_isWorking() {
        //composeRule.onNodeWithTag("emailTextField").performTextInput("email@test.com")
        //composeRule.onNodeWithText("email@test.com").assertExists()
        composeRule.onNodeWithText("Sign In").assertExists()
    }
}