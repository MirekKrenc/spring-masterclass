package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class FakePaymentService implements PaymentService {

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentRepository paymentRepository;

    @LogPayments
    @Override
    public Payment process(PaymentRequest paymentRequest) {

        var payment = Payment.builder()
                .money(paymentRequest.getMoney())
                .timestamp(Instant.now())
                .status(PaymentStatus.STARTED)
                .id(paymentIdGenerator.getNext())
                .build();
        return paymentRepository.save(payment);
    }


}
