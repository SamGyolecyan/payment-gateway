package mongodb.testing.paymentgateway.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cards")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Card {

  @Id
  private String id;

  long userId;

  double balance;

  String cardNumber;

  String cvvNumber;
}



