package com.natigbabayev.checkpoint.core.rules.patterns

import com.natigbabayev.checkpoint.core.Rule
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class RegexRuleDslTest {
    // region Mocks
    private val mockWhenInvalid: (CharSequence) -> Unit = mockk()
    // endregion

    @Test
    fun givenPattern_whenRegexRuleCalled_thenPassesPatternToBuilder() {
        // Arrange
        mockkConstructor(RegexRule.Builder::class)
        val pattern = "hello"
        // Act
        regexRule(pattern = pattern)
        // Assert
        verify { anyConstructed<RegexRule.Builder>().pattern(pattern) }
    }

    @Test
    fun givenCallbackAndPattern_whenRegexRuleCalled_thenPassesCallbackAndPatternToBuilder() {
        // Arrange
        mockkConstructor(RegexRule.Builder::class)
        val pattern = "hello"
        val capturedCallback = mutableListOf<Rule.Callback<CharSequence>>()
        // Act
        regexRule(pattern = pattern, whenInvalid = mockWhenInvalid)
        // Assert
        verify { anyConstructed<RegexRule.Builder>().whenInvalid(capture(capturedCallback)) }
        verify { anyConstructed<RegexRule.Builder>().pattern(pattern) }
    }
}
