package reactive_mongo.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactive_mongo.domain.Beer;

public interface BeerRepository extends ReactiveMongoRepository<Beer,String> {
}
