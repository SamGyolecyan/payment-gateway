package mongodb.testing.paymentgateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mongodb.testing.paymentgateway.model.PaymentRequest;
import mongodb.testing.paymentgateway.model.PaymentResponse;
import mongodb.testing.paymentgateway.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/v1/pay")
  public PaymentResponse pay(@RequestBody PaymentRequest paymentRequest) {
    log.info("PaymentRequest details: {}", paymentRequest);
    return paymentService.pay(paymentRequest);
  }


}
