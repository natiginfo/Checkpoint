package com.natigbabayev.checkpoint.core

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CheckpointDslTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: Checkpoint<String>

    // region Mocks
    private val mockRule1 = mockk<DefaultRule<String>>()
    private val mockIsValid: (input: String) -> Boolean = mockk()
    private val mockWhenInvalid: (input: String) -> Unit = mockk()
    // endregion

    @BeforeEach
    fun setup() {
        SUT = checkpoint {
            addRule(mockRule1)
            addRule {
                whenInvalid(mockWhenInvalid)
                isValid(mockIsValid)
            }
        }
    }

    @Test
    fun givenAllRulesCanPass_whenCanPassCalled_thenReturnsTrue() {
        // Arrange
        every { mockRule1.canPass(any()) } returns true
        every { mockIsValid(any()) } returns true
        // Act
        val canPass = SUT.canPass("input")
        // Assert
        assertTrue(canPass)
    }

    @Test
    fun givenDslRuleCannotPass_whenCanPassCalled_thenReturnsFalse() {
        // Arrange
        every { mockRule1.canPass(any()) } returns true
        every { mockIsValid(any()) } returns false
        every { mockWhenInvalid(any()) } answers { nothing }
        // Act
        val canPass = SUT.canPass("input")
        // Assert
        assertFalse(canPass)
    }

    @Test
    fun givenDslRuleCannotPass_whenCanPassCalled_thenInvokesWhenInvalid() {
        // Arrange
        every { mockRule1.canPass(any()) } returns true
        every { mockIsValid(any()) } returns false
        every { mockWhenInvalid(any()) } answers { nothing }
        // Act
        SUT.canPass("input")
        // Assert
        verify { mockWhenInvalid(any()) }
    }
}
