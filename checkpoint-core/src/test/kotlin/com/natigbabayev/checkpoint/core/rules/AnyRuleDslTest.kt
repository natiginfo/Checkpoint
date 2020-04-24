package com.natigbabayev.checkpoint.core.rules

import com.natigbabayev.checkpoint.core.DefaultRule
import com.natigbabayev.checkpoint.core.Rule
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class AnyRuleDslTest {
    // region Mocks
    private val mockWhenInvalid: (CharSequence) -> Unit = mockk()
    // endregion

    @Test
    fun givenParameters_whenEitherRuleCalled_thenPassesParametersToBuilder() {
        // Arrange
        mockkConstructor(AnyRule.Builder::class)
        val rules = listOf<DefaultRule<CharSequence>>(mockk(), mockk(), mockk())
        // Act
        anyRule(rules)
        // Assert
        verify { anyConstructed<AnyRule.Builder<CharSequence>>().addRules(rules) }
    }

    @Test
    fun givenParametersAndCallback_whenEitherRuleCalled_thenPassesParametersToBuilder() {
        // Arrange
        mockkConstructor(AnyRule.Builder::class)
        val rules = listOf<DefaultRule<CharSequence>>(mockk(), mockk(), mockk())
        // Act
        anyRule(rules, mockWhenInvalid)
        // Assert
        val capturedCallback = mutableListOf<Rule.Callback<CharSequence>>()
        verify { anyConstructed<AnyRule.Builder<CharSequence>>().addRules(rules) }
        verify { anyConstructed<AnyRule.Builder<CharSequence>>().whenInvalid(capture(capturedCallback)) }
    }

    @Test
    fun givenParametersAndCallback_whenWhenInvalidCalled_thenInvokesLambda() {
        // Arrange
        every { mockWhenInvalid(any()) } answers { nothing }
        mockkConstructor(AnyRule.Builder::class)
        val rules = listOf<DefaultRule<CharSequence>>(mockk(), mockk(), mockk())
        val input = "input"
        // Act
        anyRule(rules, mockWhenInvalid)
        // Assert
        val capturedCallback = mutableListOf<Rule.Callback<CharSequence>>()
        verify { anyConstructed<AnyRule.Builder<CharSequence>>().whenInvalid(capture(capturedCallback)) }
        capturedCallback.single().whenInvalid(input)
        verify { mockWhenInvalid(input) }
    }
}
