package com.natigbabayev.checkpoint.core.rules.length

import com.natigbabayev.checkpoint.core.Rule
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class MaxLengthRuleDslTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: MaxLengthRule

    // region Mocks
    private val mockWhenInvalid: (CharSequence) -> Unit = mockk()
    // endregion

    @BeforeEach
    fun setUp() {
        SUT = maxLengthRule {
            maxLength(3)
            whenInvalid(mockWhenInvalid)
        }
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInvalidInput {
        @Test
        fun whenCanPassCalled_returnsFalse() {
            // Arrange
            every { mockWhenInvalid(any()) } answers { nothing }
            // Act
            val canPass = SUT.canPass("abcd")
            // Assert
            assertFalse(canPass)
        }

        @Test
        fun whenCanPassCalled_invokesCallback() {
            // Arrange
            every { mockWhenInvalid(any()) } answers { nothing }
            val input = "abcd"
            // Act
            SUT.canPass(input)
            // Assert
            verify { mockWhenInvalid(input) }
            confirmVerified(mockWhenInvalid)
        }
    }

    @Test
    fun givenValidInput_whenCanPassCalled_returnsTrue() {
        // Arrange
        // Act
        val canPass1 = SUT.canPass("a")
        val canPass2 = SUT.canPass("ab")
        // Assert
        assertTrue(canPass1)
        assertTrue(canPass2)
    }
}
