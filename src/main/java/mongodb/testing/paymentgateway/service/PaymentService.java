package mongodb.testing.paymentgateway.service;

import mongodb.testing.paymentgateway.model.PaymentRequest;
import mongodb.testing.paymentgateway.model.PaymentResponse;

public interface PaymentService {

  PaymentResponse pay(PaymentRequest request);

}
