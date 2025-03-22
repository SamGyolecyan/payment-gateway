package mongodb.testing.paymentgateway.service.impl;

import lombok.extern.slf4j.Slf4j;
import mongodb.testing.paymentgateway.entity.Card;
import mongodb.testing.paymentgateway.model.PaymentRequest;
import mongodb.testing.paymentgateway.model.PaymentResponse;
import mongodb.testing.paymentgateway.model.PaymentStatus;
import mongodb.testing.paymentgateway.model.ValidationResult;
import mongodb.testing.paymentgateway.repository.CardRepository;
import mongodb.testing.paymentgateway.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

  private final CardRepository cardRepository;

  List<String> errorReasons = new ArrayList<>();

  public PaymentServiceImpl(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Override
  public PaymentResponse pay(PaymentRequest request) {
    Optional<Card> cardOptional = cardRepository.findByUserId(request.userId());
    if (cardOptional.isPresent()) {
      Card card = cardOptional.get();
      log.info("The card details: {}", card);

      ValidationResult validationResult = validatePayment(request, card);
      if (validationResult.isValid()){
        cardRepository.save(card);
        return PaymentResponse.builder()
            .status(PaymentStatus.SUCCESS)
            .build();
      } else {
        return PaymentResponse.builder()
            .status(PaymentStatus.FAIL)
            .errorReasons(validationResult.getErrorReasons())
            .build();
      }
    }
    return PaymentResponse.builder()
        .status(PaymentStatus.FAIL)
        .errorReasons(List.of("User does not found"))
        .build();
  }

  private ValidationResult validatePayment(PaymentRequest request, Card card) {
    boolean isCardNumberValid = request.cardNumber().equals(card.getCardNumber());
    boolean isCvvValid = request.cvvNumber().equals(card.getCvvNumber());
    boolean isEnoughMoney = false;
    double newBalance = 0;

    if (request.paymentAmount() <= card.getBalance()) {
      double oldBalance = card.getBalance();
      newBalance = oldBalance - request.paymentAmount();
      isEnoughMoney = true;
    }

    if (isCardNumberValid && isEnoughMoney && isCvvValid) {
      card.setBalance(newBalance);
      return ValidationResult.builder()
          .isValid(Boolean.TRUE)
          .build();

    } else {

      isCardNumber(isCardNumberValid);

      isEnough(isEnoughMoney);

      isCvv(isCvvValid);

      return ValidationResult.builder()
          .errorReasons(errorReasons)
          .isValid(Boolean.FALSE)
          .build();

    }

  }

  private void isCvv(boolean value) {
    if (!value) {
       errorReasons.add("Cvv is not correct");
    }
  }

  private void isEnough(boolean value) {
    if (!value) {
       errorReasons.add("Not enough money in wallet");
    }
  }

  private void isCardNumber(boolean value) {
    if (!value) {
       errorReasons.add("Not valid card number");
    }
  }




}








