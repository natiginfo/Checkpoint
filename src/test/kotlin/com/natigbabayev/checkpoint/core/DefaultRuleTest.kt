package com.natigbabayev.checkpoint.core

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TestableDefaultRule(override val callback: Callback<String>?) : DefaultRule<String>() {

    @Suppress("PropertyName")
    var _isValid: Boolean = false

    override fun isValid(input: String): Boolean {
        return _isValid
    }
}

internal class DefaultRuleTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: TestableDefaultRule

    // region Mocks
    private val mockCallback = mockk<Rule.Callback<String>>()
    // endregion

    @BeforeEach
    fun setup() {
        SUT = TestableDefaultRule(mockCallback)
        every { mockCallback.whenInvalid(any()) } answers { nothing }
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInvalidInput {
        @Test
        fun whenCanPassCalled_thenCallbackIsInvoked() {
            // Arrange
            SUT._isValid = false
            // Act
            SUT.canPass("input")
            // Assert
            verify(exactly = 1) { mockCallback.whenInvalid("input") }
        }

        @Test
        fun whenCanPassCalled_thenReturnsFalse() {
            // Arrange
            SUT._isValid = false
            // Act
            val canPass = SUT.canPass("input")
            // Assert
            assertFalse(canPass)
        }
    }

    @Nested
    @DisplayName("Given valid input")
    inner class GivenValidInput {
        @Test
        fun whenCanPassCalled_thenCallbackIsNeverInvoked() {
            // Arrange
            SUT._isValid = true
            // Act
            SUT.canPass("input")
            // Assert
            verify(exactly = 0) { mockCallback.whenInvalid("input") }
        }

        @Test
        fun whenCanPassCalled_thenReturnsTrue() {
            // Arrange
            SUT._isValid = false
            // Act
            val canPass = SUT.canPass("input")
            // Assert
            assertFalse(canPass)
        }
    }
}
