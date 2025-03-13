package mongodb.testing.paymentgateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CardCreationRequest {

  private String id;

  private String cardNumber;

  private String CVVNumber;

  private int userId;

}
