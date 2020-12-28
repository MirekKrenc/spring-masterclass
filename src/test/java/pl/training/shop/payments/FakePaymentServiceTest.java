package pl.training.shop.payments;

import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FakePaymentServiceTest {

    private static final String PAYMENT_ID = "1";
    private static final FastMoney MONEY = LocalMoney.of(1_000);
    private static final PaymentRequest PAYMENT_REQUEST = PaymentRequest.builder()
            .money(MONEY)
            .build();

    @Mock
    private PaymentIdGenerator paymentIdGenerator;
    @Mock
    private PaymentRepository paymentRepository;
    private Payment payment;

    @BeforeEach
    void setUp() {
        Mockito.when(paymentIdGenerator.getNext()).thenReturn(PAYMENT_ID);
        Mockito.when(paymentRepository.save(any(Payment.class))).then(AdditionalAnswers.returnsFirstArg());
        FakePaymentService fakePaymentService = new FakePaymentService(paymentIdGenerator, paymentRepository);
        payment = fakePaymentService.process(PAYMENT_REQUEST);
    }

    @DisplayName("Should assign generated id to created payment")
    @Test
    void shouldAssignGeneratedIdToCreatedPayment() {
        Assertions.assertEquals(payment.getId(), PAYMENT_ID);
    }

    @DisplayName("Should assign money from payment request to created payment")
    @Test
    void shouldAssignAmountFromPaymentRequestToCreatedPayment() {
        Assertions.assertEquals(MONEY, payment.getMoney());
    }

    @DisplayName("Should assign not null timestamp to created payment")
    @Test
    void shouldAssignTimestampToCreatedPayment() {
        Assertions.assertNotNull(payment.getTimestamp());
    }

    @DisplayName("Should assign STARTED status to created payment")
    @Test
    void shouldSetStartedStatusToCreatedPayment() {
        Assertions.assertEquals(PaymentStatus.STARTED, payment.getStatus());
    }

    @DisplayName("Should save created payment")
    @Test
    void shouldSaveCreatedPayment() {
        verify(paymentRepository).save(payment);
    }
}
