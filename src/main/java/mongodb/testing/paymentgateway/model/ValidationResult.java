package mongodb.testing.paymentgateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationResult {
  boolean isValid;
  List<String> errorReasons;
}
