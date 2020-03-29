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

    private val mockCallback = mockk<Rule.Callback<String>>()

    @Suppress("PrivatePropertyName")
    private val SUT = TestableRule(mockCallback)

    @BeforeEach
    fun setup() {
        every { mockCallback.whenInvalid(any()) } answers { nothing }
    }

    @Test
    fun givenIsValidTrue_whenTestInvokeCallbackCalled_thenCallbackNeverInvoked() {
        SUT.testInvokeCallback("input", true)
        verify(exactly = 0) { mockCallback.whenInvalid("input") }
    }

    @Test
    fun givenIsValidFalse_whenTestInvokeCallbackCalled_thenCallbackIsInvoked() {
        SUT.testInvokeCallback("input", false)
        verify(exactly = 1) { mockCallback.whenInvalid("input") }
    }
}
