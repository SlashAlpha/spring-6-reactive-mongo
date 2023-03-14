package reactive_mongo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactive_mongo.mappers.CustomerMapper;
import reactive_mongo.model.CustomerDTO;
import reactive_mongo.repositories.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    @Override
    public Mono<CustomerDTO> saveCustomer(CustomerDTO customerDTO) {
        return customerRepository.save(customerMapper.customerDTOTOCustomer(customerDTO)).map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(String customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId).map(customer -> {
            customer.setCustomerName(customerDTO.getCustomerName());
            return customer;
        }).flatMap(customerRepository::save).map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> getCustomerById(String customerId) {
        return customerRepository.findById(customerId).map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Flux<CustomerDTO> listCustomer() {
        return customerRepository.findAll().map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(String customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId).map(existing -> {
            if (StringUtils.hasText(customerDTO.getCustomerName())) {
                existing.setCustomerName(customerDTO.getCustomerName());
            }

            return existing;

        }).flatMap(customerRepository::save).map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Mono<Void> deleteCustomer(String customerId) {
        return customerRepository.deleteById(customerId);
    }

}
