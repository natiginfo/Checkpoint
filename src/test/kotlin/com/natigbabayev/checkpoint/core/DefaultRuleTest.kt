package com.natigbabayev.checkpoint.core

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
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

    private val mockCallback = mockk<Rule.Callback<String>>()

    @Suppress("PrivatePropertyName")
    private val SUT = spyk(TestableDefaultRule(mockCallback))

    @BeforeEach
    fun setup() {
        every { mockCallback.whenInvalid(any()) } answers { nothing }
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInvalidInput {
        @Test
        fun whenCanPassCalled_thenCallbackIsInvoked() {
            SUT._isValid = false
            SUT.canPass("input")
            verify(exactly = 1) { mockCallback.whenInvalid("input") }
        }

        @Test
        fun whenCanPassCalled_thenReturnsFalse() {
            SUT._isValid = false
            val canPass = SUT.canPass("input")
            assertFalse(canPass)
        }
    }

    @Nested
    @DisplayName("Given valid input")
    inner class GivenValidInput {
        @Test
        fun whenCanPassCalled_thenCallbackIsNeverInvoked() {
            SUT._isValid = true
            SUT.canPass("input")
            verify(exactly = 0) { mockCallback.whenInvalid("input") }
        }

        @Test
        fun whenCanPassCalled_thenReturnsTrue() {
            SUT._isValid = false
            val canPass = SUT.canPass("input")
            assertFalse(canPass)
        }
    }
}
