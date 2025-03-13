package mongodb.testing.paymentgateway.utils;

public class CardNumberUtil {

  private final static String ACBA_FIRST_CODE = "9145";
  private final static String AMERIA_FIRST_CODE = "5210";
  private final static String ARARAT_FIRST_CODE = "6289";
  private final static int QTY_NUMBERS = 16;
  private final static String CARD_PATTERN = ".*[^0-9].*";

  public boolean validate(String cardNumber) {
    String[] cardNumbers = cardNumber.split("");

    if (!cardNumber.matches(CARD_PATTERN)) {
      return false;

    } else {
      return cardNumbers.length == QTY_NUMBERS && cardNumberValidation(cardNumber);
    }
  }

  private static boolean cardNumberValidation(String cardNumber) {

    String firstFourNumber = cardNumber.substring(0, 4);

    return firstFourNumber.equals(ACBA_FIRST_CODE)
        || firstFourNumber.equals(AMERIA_FIRST_CODE)
        || firstFourNumber.equals(ARARAT_FIRST_CODE);
  }

}
