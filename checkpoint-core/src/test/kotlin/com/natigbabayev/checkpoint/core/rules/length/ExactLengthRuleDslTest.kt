package com.natigbabayev.checkpoint.core.rules.length

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ExactLengthRuleDslTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: ExactLengthRule

    // region Mocks
    private val mockWhenInvalid: (input: CharSequence) -> Unit = mockk()
    // endregion

    @BeforeEach
    fun setup() {
        SUT = exactLengthRule {
            length(5)
            whenInvalid(mockWhenInvalid)
        }
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInputIsDifferentThanWanted {
        @Test
        fun whenCanPassCalled_thenReturnsFalse() {
            // Arrange
            every { mockWhenInvalid(any()) } answers { nothing }
            val input1 = "abcd"
            val input2 = "abcdef"
            // Act
            val canPass1 = SUT.canPass(input1)
            val canPass2 = SUT.canPass(input2)
            // Assert
            assertFalse(canPass1)
            assertFalse(canPass2)
        }

        @Test
        fun whenCanPassCalled_thenInvokesCallback() {
            // Arrange
            every { mockWhenInvalid(any()) } answers { nothing }
            val input1 = "abcd"
            val input2 = "abcdef"
            // Act
            SUT.canPass(input1)
            SUT.canPass(input2)
            // Assert
            verifySequence {
                mockWhenInvalid(input1)
                mockWhenInvalid(input2)
            }
            confirmVerified(mockWhenInvalid)
        }
    }

    @Test
    fun givenInputIsSameAsWanted_whenCanPassCalled_thenReturnsTrue() {
        // Arrange
        val input = "abcde"
        // Act
        val canPass = SUT.canPass(input)
        // Assert
        assertTrue(canPass)
    }
}
