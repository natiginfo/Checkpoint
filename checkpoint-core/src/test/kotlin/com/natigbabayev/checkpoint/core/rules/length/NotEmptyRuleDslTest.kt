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

internal class NotEmptyRuleDslTest {
    @Suppress("PrivatePropertyName")
    private lateinit var SUT: NotEmptyRule

    // region Mocks
    private val mockWhenInvalid: (CharSequence) -> Unit = mockk()
    // endregion

    @BeforeEach
    fun setUp() {
        SUT = notEmptyRule(mockWhenInvalid)
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInvalidInput {
        private val invalidInput = ""

        @Test
        fun whenCanPassCalled_returnsFalse() {
            // Arrange
            every { mockWhenInvalid(any()) } answers { nothing }
            // Act
            val canPass1 = SUT.canPass(invalidInput)
            // Assert
            assertFalse(canPass1)
        }

        @Test
        fun whenCanPassCalled_invokesCallback() {
            // Arrange
            every { mockWhenInvalid(any()) } answers { nothing }
            // Act
            SUT.canPass(invalidInput)
            // Assert
            verify {
                mockWhenInvalid(invalidInput)
            }
            confirmVerified(mockWhenInvalid)
        }
    }

    @Test
    fun givenValidInput_whenCanPassCalled_returnsTrue() {
        // Arrange
        val input1 = "abcd"
        val input2 = "   "
        // Act
        val canPass1 = SUT.canPass(input1)
        val canPass2 = SUT.canPass(input2)
        // Assert
        assertTrue(canPass1)
        assertTrue(canPass2)
    }
}
