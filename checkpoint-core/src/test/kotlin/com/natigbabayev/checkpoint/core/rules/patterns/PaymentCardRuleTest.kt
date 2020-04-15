package com.natigbabayev.checkpoint.core.rules.patterns

import com.natigbabayev.checkpoint.core.Rule
import com.natigbabayev.checkpoint.core.rules.patterns.PaymentCardRule.Builder
import com.natigbabayev.checkpoint.core.rules.patterns.PaymentCardRule.CardType
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

data class TestableCard(
    val type: CardType,
    val validCard: String,
    val invalidCard: String
)

internal class TestableCardProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
        return Stream.of(
            TestableCard(
                type = CardType.AMEX,
                validCard = "340000000000009",
                invalidCard = "6799990100000000019"
            ),
            TestableCard(
                type = CardType.DINERS_CLUB,
                validCard = "30000000000004",
                invalidCard = "340000000000009"
            ),
            TestableCard(
                type = CardType.DISCOVER,
                validCard = "6011000000000004",
                invalidCard = "30000000000004"
            ),
            TestableCard(
                type = CardType.JCB,
                validCard = "3528000700000000",
                invalidCard = "5500000000000004"
            ),
            TestableCard(
                type = CardType.MAESTRO,
                validCard = "6799990100000000019",
                invalidCard = "6011000000000004"
            ),
            TestableCard(
                type = CardType.MASTERCARD,
                validCard = "5436031030606378",
                invalidCard = "6250941006528599"
            ),
            TestableCard(
                type = CardType.UNION_PAY,
                validCard = "6250941006528599",
                invalidCard = "4111111111111111"
            ),
            TestableCard(
                type = CardType.VISA,
                validCard = "4111111111111111",
                invalidCard = "3528000700000000"
            )
        ).map { Arguments.of(it) }
    }
}

internal class PaymentCardRuleTest {

    // region Mocks
    private val mockCallback: Rule.Callback<CharSequence> = mockk()
    // endregion

    private val testCards = listOf(
        TestableCard(type = CardType.AMEX, validCard = "340000000000009", invalidCard = "6799990100000000019"),
        TestableCard(type = CardType.DINERS_CLUB, validCard = "30000000000004", invalidCard = "340000000000009"),
        TestableCard(type = CardType.DISCOVER, validCard = "6011000000000004", invalidCard = "30000000000004"),
        TestableCard(type = CardType.JCB, validCard = "3528000700000000", invalidCard = "5500000000000004"),
        TestableCard(type = CardType.MAESTRO, validCard = "6799990100000000019", invalidCard = "6011000000000004"),
        TestableCard(type = CardType.MASTERCARD, validCard = "5436031030606378", invalidCard = "6250941006528599"),
        TestableCard(type = CardType.UNION_PAY, validCard = "6250941006528599", invalidCard = "4111111111111111"),
        TestableCard(type = CardType.VISA, validCard = "4111111111111111", invalidCard = "3528000700000000")
    )

    private val invalidCardNumbers = listOf(
        "1111111111111111",
        "000000000000000",
        "00000000000000",
        "0000000000000000000",
        "abcdefghijklmnopqrs"
    )
    // endregion

    @Nested
    @DisplayName("Accept all card types")
    inner class AcceptAllCardTypes {

        @Suppress("PrivatePropertyName")
        private lateinit var SUT: PaymentCardRule

        @BeforeEach
        fun setup() {
            SUT = Builder()
                .whenInvalid(mockCallback)
                .build()
        }

        @Test
        fun givenInvalidCardNumber_whenCanPassCalled_thenReturnsFalse() {
            // Arrange
            every { mockCallback.whenInvalid(any()) } answers { nothing }
            // Act
            val validationResult = invalidCardNumbers.map { SUT.canPass(it) }.toSet()
            // Assert
            assertFalse(validationResult.single())
        }

        @Test
        fun givenInvalidCardNumber_whenCanPassCalled_thenInvokesCallback() {
            // Arrange
            every { mockCallback.whenInvalid(any()) } answers { nothing }
            // Act
            invalidCardNumbers.map { SUT.canPass(it) }.toSet()
            // Assert
            verifySequence {
                invalidCardNumbers.forEach { mockCallback.whenInvalid(it) }
            }
            confirmVerified(mockCallback)
        }

        @Test
        fun givenValidCardNumbers_whenCanPassCalled_thenReturnsTrue() {
            // Arrange
            val validCardNumbers = testCards.map { it.validCard }
            // Act
            val validationResult = validCardNumbers.map { SUT.canPass(it) }.toSet()
            // Assert
            assertTrue(validationResult.single())
        }
    }

    @Nested
    @DisplayName("Accept one card type at a time")
    inner class AcceptOneCardType {

        @ParameterizedTest
        @ArgumentsSource(TestableCardProvider::class)
        fun givenValidCards_whenCanPassCalled_thenReturnsTrue(testableCard: TestableCard) {
            // Arrange
            val ruleForAcceptedCardType = Builder()
                .acceptedCardTypes(listOf(testableCard.type))
                .build()
            val input = testableCard.validCard
            // Act
            val canPass = ruleForAcceptedCardType.canPass(input)
            // Assert
            assertTrue(canPass)
        }

        @ParameterizedTest
        @ArgumentsSource(TestableCardProvider::class)
        fun givenInvalidCards_whenCanPassCalled_thenReturnsFalse(testableCard: TestableCard) {
            // Arrange
            val ruleForAcceptedCardType = Builder()
                .acceptedCardTypes(listOf(testableCard.type))
                .build()
            val input = testableCard.invalidCard
            // Act
            val canPass = ruleForAcceptedCardType.canPass(input)
            // Assert
            assertFalse(canPass)
        }

        @ParameterizedTest
        @ArgumentsSource(TestableCardProvider::class)
        fun givenInvalidCards_whenCanPassCalled_thenInvokesCallback(testableCard: TestableCard) {
            // Arrange
            val ruleForAcceptedCardType = Builder()
                .acceptedCardTypes(listOf(testableCard.type))
                .whenInvalid(mockCallback)
                .build()
            every { mockCallback.whenInvalid(any()) } answers { nothing }
            val input = testableCard.invalidCard
            // Act
            ruleForAcceptedCardType.canPass(input)
            // Assert
            verify { mockCallback.whenInvalid(input) }
            confirmVerified(mockCallback)
        }
    }
}
