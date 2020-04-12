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

internal class NotEmptyRuleTest {
    @Suppress("PrivatePropertyName")
    private lateinit var SUT: NotEmptyRule

    // region Mocks
    private val mockCallback: Rule.Callback<CharSequence> = mockk()
    // endregion

    @BeforeEach
    fun setUp() {
        SUT = NotEmptyRule.Builder()
            .whenInvalid(mockCallback)
            .build()
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInvalidInput {
        private val invalidInput = ""

        @Test
        fun whenCanPassCalled_returnsFalse() {
            // Arrange
            every { mockCallback.whenInvalid(any()) } answers { nothing }
            // Act
            val canPass1 = SUT.canPass(invalidInput)
            // Assert
            assertFalse(canPass1)
        }

        @Test
        fun whenCanPassCalled_invokesCallback() {
            // Arrange
            every { mockCallback.whenInvalid(any()) } answers { nothing }
            // Act
            SUT.canPass(invalidInput)
            // Assert
            verify {
                mockCallback.whenInvalid(invalidInput)
            }
            confirmVerified(mockCallback)
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
