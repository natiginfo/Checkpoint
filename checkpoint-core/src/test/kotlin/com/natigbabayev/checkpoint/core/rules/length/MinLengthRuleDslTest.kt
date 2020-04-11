package com.natigbabayev.checkpoint.core.rules.length

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

internal class MinLengthRuleDslTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: MinLengthRule

    // region Mocks
    private val mockWhenInvalid: (CharSequence) -> Unit = mockk()
    // endregion

    @BeforeEach
    fun setup() {
        SUT = minLengthRule {
            minLength(3)
            whenInvalid(mockWhenInvalid)
        }
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInvalidInput {
        @Test
        fun whenCanPassCalled_thenReturnsFalse() {
            // Arrange
            every { mockWhenInvalid(any()) } answers { nothing }
            // Act
            val canPass = SUT.canPass("ab")
            // Assert
            assertFalse(canPass)
        }

        @Test
        fun whenCanPassCalled_thenInvokesCallback() {
            // Arrange
            every { mockWhenInvalid(any()) } answers { nothing }
            val input = "ab"
            // Act
            SUT.canPass(input)
            // Assert
            verify { mockWhenInvalid(input) }
            confirmVerified(mockWhenInvalid)
        }
    }

    @Test
    fun givenValidInput_whenCanPassCalled_thenReturnsTrue() {
        // Arrange
        every { mockWhenInvalid(any()) } answers { nothing }
        // Act
        val canPass1 = SUT.canPass("abc")
        val canPass2 = SUT.canPass("abcd")
        // Assert
        assertTrue(canPass1)
        assertTrue(canPass2)
    }
}
