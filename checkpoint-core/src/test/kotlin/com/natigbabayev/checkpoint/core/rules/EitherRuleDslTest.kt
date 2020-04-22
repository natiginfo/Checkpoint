package com.natigbabayev.checkpoint.core.rules

import com.natigbabayev.checkpoint.core.DefaultRule
import com.natigbabayev.checkpoint.core.Rule
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class EitherRuleDslTest {
    // region Mocks
    private val mockWhenInvalid: (CharSequence) -> Unit = mockk()
    // endregion

    @Test
    fun givenParameters_whenEitherRuleCalled_thenPassesParametersToBuilder() {
        // Arrange
        mockkConstructor(EitherRule.Builder::class)
        val rules = listOf<DefaultRule<CharSequence>>(mockk(), mockk(), mockk())
        // Act
        eitherRule(rules)
        // Assert
        verify { anyConstructed<EitherRule.Builder<CharSequence>>().addRules(rules) }
    }

    @Test
    fun givenParametersAndCallback_whenEitherRuleCalled_thenPassesParametersToBuilder() {
        // Arrange
        mockkConstructor(EitherRule.Builder::class)
        val rules = listOf<DefaultRule<CharSequence>>(mockk(), mockk(), mockk())
        // Act
        eitherRule(rules, mockWhenInvalid)
        // Assert
        val capturedCallback = mutableListOf<Rule.Callback<CharSequence>>()
        verify { anyConstructed<EitherRule.Builder<CharSequence>>().addRules(rules) }
        verify { anyConstructed<EitherRule.Builder<CharSequence>>().whenInvalid(capture(capturedCallback)) }
    }

    @Test
    fun givenParametersAndCallback_whenWhenInvalidCalled_thenInvokesLambda() {
        // Arrange
        every { mockWhenInvalid(any()) } answers { nothing }
        mockkConstructor(EitherRule.Builder::class)
        val rules = listOf<DefaultRule<CharSequence>>(mockk(), mockk(), mockk())
        val input = "input"
        // Act
        eitherRule(rules, mockWhenInvalid)
        // Assert
        val capturedCallback = mutableListOf<Rule.Callback<CharSequence>>()
        verify { anyConstructed<EitherRule.Builder<CharSequence>>().whenInvalid(capture(capturedCallback)) }
        capturedCallback.single().whenInvalid(input)
        verify { mockWhenInvalid(input) }
    }
}
