package com.natigbabayev.checkpoint.core.rxjava2

import com.natigbabayev.checkpoint.core.DefaultRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class DefaultRuleExtensionsTest {
    // region Mocks
    private val mockDefaultRule = mockk<DefaultRule<CharSequence>>()
    // endregion

    @Test
    fun givenDefaultRule_whenCanPassSingleCalled_thenDoesNotInvokeDefaultRule() {
        // Arrange
        every { mockDefaultRule.canPass(any()) } returns true
        val input = "input"
        // Act
        mockDefaultRule.canPassSingle(input)
        // Assert
        verify(exactly = 0) {
            mockDefaultRule.canPass(input)
        }
    }

    @Test
    fun givenDefaultRule_whenCanPassSingleSubscribed_thenInvokesDefaultRule() {
        // Arrange
        every { mockDefaultRule.canPass(any()) } returns true
        val input = "input"
        // Act
        mockDefaultRule.canPassSingle(input).test()
        // Assert
        verify(exactly = 1) {
            mockDefaultRule.canPass(input)
        }
    }

    @Test
    fun givenCanPassReturnsTrue_whenCanPassSingleSubscribed_thenReturnsTrue() {
        // Arrange
        every { mockDefaultRule.canPass(any()) } returns true
        val input = "input"
        // Act
        val result = mockDefaultRule.canPassSingle(input).test()
        // Assert
        result.assertValue(true)
    }

    @Test
    fun givenCanPassReturnsFalse_whenCanPassSingleSubscribed_thenReturnsFalse() {
        // Arrange
        every { mockDefaultRule.canPass(any()) } returns false
        val input = "input"
        // Act
        val result = mockDefaultRule.canPassSingle(input).test()
        // Assert
        result.assertValue(false)
    }
}
