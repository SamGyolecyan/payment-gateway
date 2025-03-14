package mongodb.testing.paymentgateway.service.impl;

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

@Service
public class PaymentServiceImpl implements PaymentService {

  private final CardRepository cardRepository;

  public PaymentServiceImpl(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Override
  public PaymentResponse pay(PaymentRequest request) {
    Optional<Card> cardOptional = cardRepository.findByUserId(request.userId());
    if (cardOptional.isPresent()) {
      Card card = cardOptional.get();
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
    List<String> errorReasons = new ArrayList<>();
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

    }else {
      if (!isCardNumberValid){
        errorReasons.add("Not valid card number");
      }
      if (!isEnoughMoney){
        errorReasons.add("Not enough money in wallet");
      }
      if (!isCvvValid){
        errorReasons.add("Cvv is not correct");
      }
      return ValidationResult.builder()
          .errorReasons(errorReasons)
          .isValid(Boolean.FALSE)
          .build();

    }
  }
}
