package old;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.training.shop.payments.FakePaymentService;
import pl.training.shop.payments.Payment;
import pl.training.shop.payments.PaymentRequest;
import pl.training.shop.payments.PaymentService;

@Component
@Log
@RequiredArgsConstructor
public class LoggingPaymentService implements PaymentService {

    private static final String LOG_FORMAT = "A new payment of %s has been initiated";

    private final FakePaymentService paymentService;

    @Override
    public Payment process(PaymentRequest paymentRequest) {
        var payment = paymentService.process(paymentRequest);
        log.info(createLogEntry(payment));
        return payment;
    }

    private String createLogEntry(Payment payment) {
        return String.format(LOG_FORMAT, payment.getMoney());
    }
}
