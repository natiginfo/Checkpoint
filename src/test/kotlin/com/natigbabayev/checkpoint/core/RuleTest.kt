package com.natigbabayev.checkpoint.core

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


internal class TestableRule(sideEffect: ((String) -> Unit)?) : Rule<String, Boolean>(sideEffect) {
    override fun isValid(input: String): Boolean {
        TODO("not implemented")
    }

    override fun canPass(input: String): Boolean {
        TODO("not implemented")
    }

    fun testInvokeSideEffect(input: String, isValid: Boolean) {
        invokeSideEffect(input, isValid)
    }
}

internal class RuleTest {

    private val mockSideEffect = mockk<(String) -> Unit>()
    @Suppress("PrivatePropertyName")
    private val SUT = TestableRule(mockSideEffect)

    @BeforeEach
    fun setup() {
        every { mockSideEffect.invoke(any()) } returns Unit
    }

    @Test
    fun givenIsValidTrue_whenTestInvokeSideEffectCalled_thenSideEffectIsNeverInvoked() {
        SUT.testInvokeSideEffect("input", true)
        verify(exactly = 0) { mockSideEffect.invoke("input") }
    }

    @Test
    fun givenIsValidFalse_whenTestInvokeSideEffectCalled_thenSideEffectIsInvoked() {
        SUT.testInvokeSideEffect("input", false)
        verify(exactly = 1) { mockSideEffect.invoke("input") }
    }
}
