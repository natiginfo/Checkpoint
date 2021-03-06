package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.Rule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class LengthRangeRuleTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: LengthRangeRule

    // region Mocks
    private val mockCallback: Rule.Callback<CharSequence> = mockk()
    // endregion

    @BeforeEach
    fun setup() {
        SUT = LengthRangeRule.Builder()
            .whenInvalid(mockCallback)
            .minLength(3)
            .maxLength(6)
            .build()
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInvalidInput {
        private val invalidInput1 = "ab"
        private val invalidInput2 = "abcdefg"

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
        }
    }

    @Test
    fun givenValidInput_whenCanPassCalled_returnsTrue() {
        // Arrange
        // Act
        val results = listOf(
            SUT.canPass("abc"),
            SUT.canPass("abcd"),
            SUT.canPass("abcde"),
            SUT.canPass("abcdef")
        )
        // Assert
        assertTrue(results.all { it == true })
    }
}
