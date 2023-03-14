package reactive_mongo.services;

import reactive_mongo.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {
    Mono<BeerDTO> saveBeer(BeerDTO beerDTO);
    Mono<BeerDTO> updateBeer(String beerId,BeerDTO beerDTO);
    Mono<BeerDTO> getBeerById(String beerId);
    Flux<BeerDTO> listBeers();
    Mono<BeerDTO> patchBeer(String beerId,BeerDTO beerDTO);
    Mono<Void> deleteBeer(String beerId);

}
