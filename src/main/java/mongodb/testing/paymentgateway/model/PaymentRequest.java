package mongodb.testing.paymentgateway.model;

public record PaymentRequest(
    long userId,
    double paymentAmount,
    String cardNumber,
    String CVVNumber
) {}
