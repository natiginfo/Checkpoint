package com.natigbabayev.checkpoint.core

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TestableRule(override val callback: Callback<String>) : Rule<String, Boolean>() {

    override fun isValid(input: String): Boolean {
        TODO("not implemented")
    }

    override fun canPass(input: String): Boolean {
        TODO("not implemented")
    }

    fun testInvokeCallback(input: String, isValid: Boolean) {
        invokeCallback(input, isValid)
    }
}

internal class RuleTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: TestableRule

    // region Mocks
    private val mockCallback = mockk<Rule.Callback<String>>()
    // endregion

    @BeforeEach
    fun setup() {
        SUT = TestableRule(mockCallback)
        every { mockCallback.whenInvalid(any()) } answers { nothing }
    }

    @Test
    fun givenIsValidTrue_whenTestInvokeCallbackCalled_thenCallbackNeverInvoked() {
        // Arrange
        val input = "input"
        // Act
        SUT.testInvokeCallback(input, true)
        // Assert
        verify(exactly = 0) { mockCallback.whenInvalid(input) }
    }

    @Test
    fun givenIsValidFalse_whenTestInvokeCallbackCalled_thenCallbackIsInvoked() {
        // Arrange
        val input = "input"
        // Act
        SUT.testInvokeCallback(input, false)
        // Assert
        verify(exactly = 1) { mockCallback.whenInvalid(input) }
    }
}
