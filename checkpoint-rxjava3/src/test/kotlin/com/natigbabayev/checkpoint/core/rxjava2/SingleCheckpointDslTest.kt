package com.natigbabayev.checkpoint.core.rxjava2

import com.natigbabayev.checkpoint.core.DefaultRule
import com.natigbabayev.checkpoint.core.DefaultRuleBuilder
import com.natigbabayev.checkpoint.core.whenInvalid
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SingleCheckpointDslTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: SingleCheckpoint<CharSequence>

    private lateinit var fakeDefaultRule: DefaultRule<CharSequence>

    // region Mocks
    private val mockRule = mockk<SingleRule<CharSequence>>()
    private val mockIsValid: (input: CharSequence) -> Boolean = mockk()
    private val mockIsDefaultValid: (input: CharSequence) -> Boolean = mockk()
    private val mockWhenInvalid: (input: CharSequence) -> Unit = mockk()
    private val mockWhenDefaultRuleInvalid: (input: CharSequence) -> Unit = mockk()
    // endregion

    @BeforeEach
    fun setup() {
        fakeDefaultRule = DefaultRuleBuilder<CharSequence>()
            .isValid(mockIsDefaultValid)
            .whenInvalid(mockWhenDefaultRuleInvalid)
            .build()

        SUT = singleCheckpoint {
            addRule(mockRule)
            addRule(fakeDefaultRule)
            addRule {
                whenInvalid(mockWhenInvalid)
                isValid(mockIsValid)
            }
        }
    }

    @Test
    fun givenAllRulesCanPass_whenCanPassCalled_thenReturnsTrue() {
        // Arrange
        every { mockRule.canPass(any()) } returns Single.just(true)
        every { mockIsDefaultValid(any()) } returns true
        every { mockIsValid(any()) } returns true
        // Act
        val result = SUT.canPass("input").test()
        // Assert
        result.assertValue(true)
    }

    @Test
    fun givenDslRuleCannotPass_whenCanPassCalled_thenReturnsFalse() {
        // Arrange
        every { mockRule.canPass(any()) } returns Single.just(true)
        every { mockIsDefaultValid(any()) } returns true
        every { mockIsValid(any()) } returns false
        every { mockWhenInvalid(any()) } answers { nothing }
        // Act
        val result = SUT.canPass("input").test()
        // Assert
        result.assertValue(false)
    }

    @Test
    fun givenDslRuleCannotPass_whenCanPassCalled_thenInvokesWhenInvalid() {
        // Arrange
        every { mockRule.canPass(any()) } returns Single.just(true)
        every { mockIsDefaultValid(any()) } returns true
        every { mockIsValid(any()) } returns false
        every { mockWhenInvalid(any()) } answers { nothing }
        // Act
        SUT.canPass("input").test()
        // Assert
        verify { mockWhenInvalid(any()) }
    }

    @Test
    fun givenDefaultRuleCannotPass_whenCanPassCalled_thenReturnsFalse() {
        // Arrange
        every { mockRule.canPass(any()) } returns Single.just(true)
        every { mockIsDefaultValid(any()) } returns false
        every { mockIsValid(any()) } returns true
        every { mockWhenInvalid(any()) } answers { nothing }
        // Act
        val result = SUT.canPass("input").test()
        // Assert
        result.assertValue(false)
    }

    @Test
    fun givenDefaultRuleCannotPass_whenCanPassCalled_thenInvokesWhenInvalid() {
        // Arrange
        every { mockRule.canPass(any()) } returns Single.just(true)
        every { mockIsDefaultValid(any()) } returns false
        every { mockIsValid(any()) } returns true
        every { mockWhenInvalid(any()) } answers { nothing }
        // Act
        SUT.canPass("input").test()
        // Assert
        verify { mockWhenDefaultRuleInvalid(any()) }
    }
}
