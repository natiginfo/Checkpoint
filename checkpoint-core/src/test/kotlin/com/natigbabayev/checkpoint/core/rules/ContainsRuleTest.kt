package com.natigbabayev.checkpoint.core.rules

import com.natigbabayev.checkpoint.core.Rule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ContainsRuleTest {

    // region Mocks
    private val mockCallback: Rule.Callback<CharSequence> = mockk()
    // endregion

    @Test
    fun givenInputThatContainsOther_whenCanPassCalled_thenReturnsTrue() {
        // Arrange
        val input1 = "test"
        val input2 = "TeSt"
        val other = "te"
        val rule = ContainsRule.Builder()
            .other(other)
            .ignoreCase(true)
            .build()
        // Act
        val canPass1 = rule.canPass(input1)
        val canPass2 = rule.canPass(input2)
        // Assert
        assertTrue(canPass1)
        assertTrue(canPass2)
    }

    @Test
    fun givenInputThatDoesntContainOther_whenCanPassCalled_thenReturnsFalse() {
        // Arrange
        val input1 = "Tst"
        val input2 = "TsT"
        val other = "te"
        val rule = ContainsRule.Builder()
            .other(other)
            .build()
        // Act
        val canPass1 = rule.canPass(input1)
        val canPass2 = rule.canPass(input2)
        // Assert
        assertFalse(canPass1)
        assertFalse(canPass2)
    }

    @Test
    fun givenInputThatDoesntContainOther_whenCanPassCalled_thenInvokesCallback() {
        // Arrange
        every { mockCallback.whenInvalid(any()) } answers { nothing }
        val input1 = "Tst"
        val input2 = "Test"
        val other = "te"
        val rule = ContainsRule.Builder()
            .other(other)
            .whenInvalid(mockCallback)
            .build()
        // Act
        rule.canPass(input1)
        rule.canPass(input2)
        // Assert
        val capturedInputs = mutableListOf<CharSequence>()
        verify(exactly = 2) { mockCallback.whenInvalid(capture(capturedInputs)) }
        assertTrue(capturedInputs.contains(input1))
        assertTrue(capturedInputs.contains(input2))
    }
}
