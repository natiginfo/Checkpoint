package com.natigbabayev.checkpoint.core.rules.patterns

import com.natigbabayev.checkpoint.core.Rule
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

data class RegexRuleArgument(
    val pattern: String,
    val validInputs: List<CharSequence>,
    val invalidInputs: List<CharSequence>
)

class RegexRuleArgumentsProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
        return Stream.of(
            RegexRuleArgument(
                pattern = "hello",
                validInputs = listOf("hello"),
                invalidInputs = listOf("Hello", "ello", "", "   ")
            ),
            RegexRuleArgument(
                pattern = "b[aeiou]bble",
                validInputs = "babble, bebble, bibble, bobble, bubble".split(", "),
                invalidInputs = listOf("Hello", "ello", "", "   ", "bbble")
            )
        ).map { Arguments.of(it) }
    }
}

internal class RegexRuleTest {
    // region Mocks
    private val mockCallback: Rule.Callback<CharSequence> = mockk()
    // endregion

    @ParameterizedTest
    @ArgumentsSource(RegexRuleArgumentsProvider::class)
    fun givenValidInputs_whenCanPassCalled_thenReturnsTrue(argument: RegexRuleArgument) {
        // Arrange
        val rule = RegexRule.Builder()
            .pattern(argument.pattern)
            .build()
        val inputs = argument.validInputs
        // Act
        val validationResult = inputs.map { rule.canPass(it) }.toSet()
        // Assert
        assertTrue(validationResult.single())
    }

    @ParameterizedTest
    @ArgumentsSource(RegexRuleArgumentsProvider::class)
    fun givenInvalidInputs_whenCanPassCalled_thenReturnsFalse(argument: RegexRuleArgument) {
        // Arrange
        val rule = RegexRule.Builder()
            .pattern(argument.pattern)
            .build()
        val inputs = argument.invalidInputs
        // Act
        val validationResult = inputs.map { rule.canPass(it) }.toSet()
        // Assert
        assertFalse(validationResult.single())
    }

    @ParameterizedTest
    @ArgumentsSource(RegexRuleArgumentsProvider::class)
    fun givenInvalidInputs_whenCanPassCalled_thenInvokesCallback(argument: RegexRuleArgument) {
        // Arrange
        val rule = RegexRule.Builder()
            .pattern(argument.pattern)
            .whenInvalid(mockCallback)
            .build()
        every { mockCallback.whenInvalid(any()) } answers { nothing }
        val inputs = argument.invalidInputs
        // Act
        inputs.forEach { rule.canPass(it) }
        // Assert
        val capturedCallbackArguments = mutableListOf<CharSequence>()
        verify(exactly = inputs.size) { mockCallback.whenInvalid(capture(capturedCallbackArguments)) }
        assertEquals(inputs, capturedCallbackArguments)
        confirmVerified(mockCallback)
    }
}
