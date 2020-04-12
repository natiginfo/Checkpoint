package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.Rule
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

internal class NotBlankRuleTest {
    @Suppress("PrivatePropertyName")
    private lateinit var SUT: NotBlankRule

    // region Mocks
    private val mockCallback: Rule.Callback<CharSequence> = mockk()
    // endregion

    @BeforeEach
    fun setUp() {
        SUT = NotBlankRule.Builder()
            .whenInvalid(mockCallback)
            .build()
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInvalidInput {
        private val invalidInput1 = ""
        private val invalidInput2 = "    "

        @Test
        fun whenCanPassCalled_returnsFalse() {
            // Arrange
            every { mockCallback.whenInvalid(any()) } answers { nothing }
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
            every { mockCallback.whenInvalid(any()) } answers { nothing }
            // Act
            SUT.canPass(invalidInput1)
            SUT.canPass(invalidInput2)
            // Assert
            verifySequence {
                mockCallback.whenInvalid(invalidInput1)
                mockCallback.whenInvalid(invalidInput2)
            }
            confirmVerified(mockCallback)
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
