package com.natigbabayev.checkpoint.core.rxjava2

import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CheckpointTest {

    @Suppress("PrivatePropertyName")
    private lateinit var SUT: SingleCheckpoint<CharSequence>

    // region Mocks
    private val mockRule1 = mockk<SingleRule<CharSequence>>()
    private val mockRule2 = mockk<SingleRule<CharSequence>>()
    // endregion

    @BeforeEach
    fun setup() {
        SUT = SingleCheckpoint.Builder<CharSequence>()
            .addRules(listOf(mockRule1, mockRule2))
            .build()
    }

    @Test
    fun givenAllRulesCanPass_whenCanPassCalled_thenReturnsTrue() {
        // Arrange
        every { mockRule1.canPass(any()) } returns Single.just(true)
        every { mockRule2.canPass(any()) } returns Single.just(true)
        // Act
        val result = SUT.canPass("input").test()
        // Assert
        result.assertValue(true)
    }

    @Test
    fun givenOneOfRulesCannotPass_whenCanPassCalled_returnsFalse() {
        // Arrange
        every { mockRule1.canPass(any()) } returns Single.just(true)
        every { mockRule2.canPass(any()) } returns Single.just(false)
        // Act
        val result = SUT.canPass("input").test()
        // Assert
        result.assertValue(false)
    }
}
