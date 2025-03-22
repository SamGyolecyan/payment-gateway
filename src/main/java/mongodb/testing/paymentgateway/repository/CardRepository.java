package mongodb.testing.paymentgateway.repository;

import mongodb.testing.paymentgateway.entity.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CardRepository extends MongoRepository<Card, String> {
    Optional<Card> findByUserId(Long userId);
}
