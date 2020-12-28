package pl.training.shop.payments;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/*
enables aspect globally in application
the @EnableAspectJAutoProxy annotation can be added for example to component FakePaymentService
 */

@EnableAspectJAutoProxy
@Configuration
public class PaymentsConfiguration {
}
