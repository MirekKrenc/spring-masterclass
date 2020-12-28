package pl.training.shop.payments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.Long.parseLong;

public class IncrementalPaymentIdGeneratorTest {

    private static final String ID_FORMAT = "\\d{10}";

    private final IncrementalPaymentIdGenerator paymentIdGenerator = new IncrementalPaymentIdGenerator();

    @DisplayName("Should generate valid incremental id")
    @Test
    void shouldGenerateValidId() {
        final String id = paymentIdGenerator.getNext();
        Assertions.assertTrue(id.matches(ID_FORMAT));
    }

    @DisplayName("should generate incrementing value")
    @Test
    void shouldGenerateIncrementingValueToPreviousOne() {
        long firstIdValue = parseLong(paymentIdGenerator.getNext());
        long secondIdValue = parseLong(paymentIdGenerator.getNext());
        Assertions.assertEquals(firstIdValue +1, secondIdValue);
    }
}
