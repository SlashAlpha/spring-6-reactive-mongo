package reactive_mongo.services;

import reactive_mongo.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<CustomerDTO> saveCustomer(CustomerDTO customerDTO);
    Mono<CustomerDTO> updateCustomer(String customerId,CustomerDTO customerDTO);
    Mono<CustomerDTO> getCustomerById(String customerId);
    Flux<CustomerDTO> listCustomer();
    Mono<CustomerDTO> patchCustomer(String customerId,CustomerDTO customerDTO);
    Mono<Void> deleteCustomer(String beerId);

}
