package com.natigbabayev.checkpoint.core.rules

import com.natigbabayev.checkpoint.core.Rule
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class ContainsRuleDslTest {
    // region Mocks
    private val mockWhenInvalid: (CharSequence) -> Unit = mockk()
    // endregion

    @Test
    fun givenParameters_whenContainsRuleCalled_thenPassesParametersToBuilder() {
        // Arrange
        mockkConstructor(ContainsRule.Builder::class)
        val other = "other"
        val ignoreCase = true
        // Act
        containsRule(other = other, ignoreCase = ignoreCase)
        // Assert
        verify { anyConstructed<ContainsRule.Builder>().other(other) }
        verify { anyConstructed<ContainsRule.Builder>().ignoreCase(true) }
    }

    @Test
    fun givenParametersAndCallback_whenContainsRuleCalled_thenPassesParametersToBuilder() {
        // Arrange
        mockkConstructor(ContainsRule.Builder::class)
        val other = "other"
        val ignoreCase = true
        // Act
        containsRule(
            other = other,
            ignoreCase = ignoreCase,
            whenInvalid = mockWhenInvalid
        )
        // Assert
        val capturedCallback = mutableListOf<Rule.Callback<CharSequence>>()
        verify { anyConstructed<ContainsRule.Builder>().other(other) }
        verify { anyConstructed<ContainsRule.Builder>().ignoreCase(ignoreCase) }
        verify { anyConstructed<ContainsRule.Builder>().whenInvalid(capture(capturedCallback)) }
    }

    @Test
    fun givenParametersAndCallback_whenWhenInvalidCalled_thenInvokesLambda() {
        // Arrange
        mockkConstructor(ContainsRule.Builder::class)
        every { mockWhenInvalid(any()) } answers { nothing }
        val input = "input"
        val other = ""
        // Act
        containsRule(
            other = other,
            whenInvalid = mockWhenInvalid
        )
        // Assert
        val capturedCallback = mutableListOf<Rule.Callback<CharSequence>>()
        verify { anyConstructed<ContainsRule.Builder>().whenInvalid(capture(capturedCallback)) }
        capturedCallback.single().whenInvalid(input)
        verify { mockWhenInvalid(input) }
    }
}
