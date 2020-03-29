package com.natigbabayev.checkpoint.core

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class CheckpointTest {

    private val mockRule1 = mockk<DefaultRule<String>>()
    private val mockRule2 = mockk<DefaultRule<String>>()

    @Suppress("PrivatePropertyName")
    private val SUT = Checkpoint.Builder<String>().addRule(mockRule1, mockRule2).build()

    @Test
    fun givenAllRulesCanPass_whenCanPassCalled_thenReturnsTrue() {
        every { mockRule1.canPass(any()) } returns true
        every { mockRule2.canPass(any()) } returns true
        val canPass = SUT.canPass("input")
        assertTrue(canPass)
    }

    @Test
    fun givenOneOfRulesCannotPass_whenCanPassCalled_returnsFalse() {
        every { mockRule1.canPass(any()) } returns true
        every { mockRule2.canPass(any()) } returns false
        val canPass = SUT.canPass("input")
        assertFalse(canPass)
    }
}
