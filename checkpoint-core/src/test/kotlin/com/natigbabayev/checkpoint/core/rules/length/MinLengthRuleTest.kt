package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.Rule
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class MinLengthRuleTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: MinLengthRule

    // region Mocks
    private val mockCallback: Rule.Callback<CharSequence> = mockk()
    // endregion

    @BeforeEach
    fun setUp() {
        SUT = MinLengthRule.Builder()
            .minLength(3)
            .whenInvalid(mockCallback)
            .build()
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInvalidInput {
        @Test
        fun whenCanPassCalled_thenReturnsFalse() {
            // Arrange
            every { mockCallback.whenInvalid(any()) } answers { nothing }
            // Act
            val canPass = SUT.canPass("ab")
            // Assert
            assertFalse(canPass)
        }

        @Test
        fun whenCanPassCalled_thenInvokesCallback() {
            // Arrange
            every { mockCallback.whenInvalid(any()) } answers { nothing }
            val input = "ab"
            // Act
            SUT.canPass(input)
            // Assert
            verify { mockCallback.whenInvalid(input) }
            confirmVerified(mockCallback)
        }
    }

    @Test
    fun givenValidInput_whenCanPassCalled_thenReturnsTrue() {
        // Arrange
        every { mockCallback.whenInvalid(any()) } answers { nothing }
        // Act
        val canPass1 = SUT.canPass("abc")
        val canPass2 = SUT.canPass("abcd")
        // Assert
        assertTrue(canPass1)
        assertTrue(canPass2)
    }
}
