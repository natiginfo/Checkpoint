package com.natigbabayev.checkpoint.core.rules

import com.natigbabayev.checkpoint.core.DefaultRule
import com.natigbabayev.checkpoint.core.Rule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class EitherRuleTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: EitherRule<CharSequence>

    // region Mocks
    private val mockCallback: Rule.Callback<CharSequence> = mockk()
    private val mockRules: List<DefaultRule<CharSequence>> = listOf(
        mockk(), mockk(), mockk()
    )
    // endregion

    @BeforeEach
    fun setup() {
        SUT = EitherRule.Builder<CharSequence>()
            .addRules(mockRules)
            .whenInvalid(mockCallback)
            .build()

        every { mockCallback.whenInvalid(any()) } answers { nothing }
    }

    @Test
    fun givenAllRulesCanPass_whenCanPassCalled_returnsTrue() {
        // Arrange
        mockRules.forEach { every { it.canPass(any()) } returns true }
        // Act
        val canPass = SUT.canPass("input")
        // Assert
        assertTrue(canPass)
    }

    @Test
    fun givenOnlyOneRuleCanPass_whenCanPassCalled_returnsTrue() {
        // Arrange
        mockRules.take(2).forEach { every { it.canPass(any()) } returns true }
        every { mockRules.last().canPass(any()) } returns true
        // Act
        val canPass = SUT.canPass("input")
        // Assert
        assertTrue(canPass)
    }

    @Test
    fun givenNoRuleCanPass_whenCanPassCalled_returnsFalse() {
        // Arrange
        mockRules.forEach { every { it.canPass(any()) } returns false }
        // Act
        val canPass = SUT.canPass("input")
        // Assert
        assertFalse(canPass)
    }

    @Test
    fun givenNoRuleCanPass_whenCanPassCalled_invokesCallback() {
        // Arrange
        mockRules.forEach { every { it.canPass(any()) } returns false }
        val input = "input"
        // Act
        SUT.canPass(input)
        // Assert
        verify { mockCallback.whenInvalid(input) }
    }
}
