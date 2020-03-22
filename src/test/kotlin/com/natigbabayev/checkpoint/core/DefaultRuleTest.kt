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

internal class TestableDefaultRule(sideEffect: ((String) -> Unit)?) : DefaultRule<String>(sideEffect) {
    @Suppress("PropertyName")
    var _isValid: Boolean = false

    override fun isValid(input: String): Boolean {
        return _isValid
    }
}

internal class DefaultRuleTest {

    private val mockSideEffect = mockk<(String) -> Unit>()
    @Suppress("PrivatePropertyName")
    private val SUT = spyk(TestableDefaultRule(mockSideEffect))

    @BeforeEach
    fun setup() {
        every { mockSideEffect.invoke(any()) } returns Unit
    }

    @Nested
    @DisplayName("Given invalid input")
    inner class GivenInvalidInput {
        @Test
        fun whenCanPassCalled_thenSideEffectIsInvoked() {
            SUT._isValid = false
            SUT.canPass("input")
            verify(exactly = 1) { mockSideEffect.invoke("input") }
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
        fun whenCanPassCalled_thenSideEffectIsNeverInvoked() {
            SUT._isValid = true
            SUT.canPass("input")
            verify(exactly = 0) { mockSideEffect.invoke("input") }
        }

        @Test
        fun whenCanPassCalled_thenReturnsTrue() {
            SUT._isValid = false
            val canPass = SUT.canPass("input")
            assertFalse(canPass)
        }
    }
}
