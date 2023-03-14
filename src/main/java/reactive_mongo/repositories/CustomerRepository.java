package reactive_mongo.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactive_mongo.domain.Customer;

public interface CustomerRepository extends ReactiveMongoRepository<Customer,String> {
}
