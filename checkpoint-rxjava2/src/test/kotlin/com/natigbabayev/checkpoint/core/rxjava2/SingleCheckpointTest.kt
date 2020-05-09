package com.natigbabayev.checkpoint.core.rxjava2

import com.natigbabayev.checkpoint.core.DefaultRule
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SingleCheckpointTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: SingleCheckpoint<CharSequence>

    // region Mocks
    private val mockSingleRule1 = mockk<SingleRule<CharSequence>>()
    private val mockSingleRule2 = mockk<SingleRule<CharSequence>>()
    private val mockDefaultRule1 = mockk<DefaultRule<CharSequence>>()
    private val mockDefaultRule2 = mockk<DefaultRule<CharSequence>>()
    // endregion

    @BeforeEach
    fun setup() {
        SUT = SingleCheckpoint.Builder<CharSequence>()
            .addRules(listOf(mockSingleRule1, mockSingleRule2))
            .addRules(listOf(mockDefaultRule1, mockDefaultRule2))
            .build()
    }

    @Test
    fun givenAllRulesCanPass_whenCanPassCalled_thenReturnsTrue() {
        // Arrange
        every { mockSingleRule1.canPass(any()) } returns Single.just(true)
        every { mockSingleRule2.canPass(any()) } returns Single.just(true)
        every { mockDefaultRule1.canPass(any()) } returns true
        every { mockDefaultRule2.canPass(any()) } returns true
        // Act
        val result = SUT.canPass("input").test()
        // Assert
        result.assertValue(true)
    }

    @Test
    fun givenOneOfRulesCannotPass_whenCanPassCalled_returnsFalse() {
        // Arrange
        every { mockSingleRule1.canPass(any()) } returns Single.just(true)
        every { mockSingleRule2.canPass(any()) } returns Single.just(false)
        every { mockDefaultRule1.canPass(any()) } returns true
        every { mockDefaultRule2.canPass(any()) } returns false

        // Act
        val result = SUT.canPass("input").test()
        // Assert
        result.assertValue(false)
    }
}
