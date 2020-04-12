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

internal class NotBlankRuleDslTest {
    @Suppress("PrivatePropertyName")
    private lateinit var SUT: NotBlankRule

    // region Mocks
    private val mockWhenInvalid: (CharSequence) -> Unit = mockk()
    // endregion

    @BeforeEach
    fun setUp() {
        SUT = notBlankRule(whenInvalid = mockWhenInvalid)
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInvalidInput {
        private val invalidInput1 = ""
        private val invalidInput2 = "    "

        @Test
        fun whenCanPassCalled_returnsFalse() {
            // Arrange
            every { mockWhenInvalid(any()) } answers { nothing }
            // Act
            val canPass1 = SUT.canPass(invalidInput1)
            val canPass2 = SUT.canPass(invalidInput2)
            // Assert
            assertFalse(canPass1)
            assertFalse(canPass2)
        }

        @Test
        fun whenCanPassCalled_invokesCallback() {
            // Arrange
            every { mockWhenInvalid(any()) } answers { nothing }
            // Act
            SUT.canPass(invalidInput1)
            SUT.canPass(invalidInput2)
            // Assert
            verifySequence {
                mockWhenInvalid(invalidInput1)
                mockWhenInvalid(invalidInput2)
            }
            confirmVerified(mockWhenInvalid)
        }
    }

    @Test
    fun givenValidInput_whenCanPassCalled_returnsTrue() {
        // Arrange
        val input = "abcd"
        // Act
        val canPass = SUT.canPass(input)
        // Assert
        assertTrue(canPass)
    }
}
