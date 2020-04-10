package com.natigbabayev.checkpoint.core

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CheckpointTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: Checkpoint<String>

    // region Mocks
    private val mockRule1 = mockk<DefaultRule<String>>()
    private val mockRule2 = mockk<DefaultRule<String>>()
    // endregion

    @BeforeEach
    fun setup() {
        SUT = Checkpoint.Builder<String>().addRules(listOf(mockRule1, mockRule2)).build()
    }

    @Test
    fun givenAllRulesCanPass_whenCanPassCalled_thenReturnsTrue() {
        // Arrange
        every { mockRule1.canPass(any()) } returns true
        every { mockRule2.canPass(any()) } returns true
        // Act
        val canPass = SUT.canPass("input")
        // Assert
        assertTrue(canPass)
    }

    @Test
    fun givenOneOfRulesCannotPass_whenCanPassCalled_returnsFalse() {
        // Arrange
        every { mockRule1.canPass(any()) } returns true
        every { mockRule2.canPass(any()) } returns false
        // Act
        val canPass = SUT.canPass("input")
        // Assert
        assertFalse(canPass)
    }
}
