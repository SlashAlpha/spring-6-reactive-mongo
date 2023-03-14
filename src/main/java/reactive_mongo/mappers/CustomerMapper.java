package reactive_mongo.mappers;

import org.mapstruct.Mapper;
import reactive_mongo.domain.Customer;
import reactive_mongo.model.CustomerDTO;

@Mapper
public interface CustomerMapper {
    Customer customerDTOTOCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDTO(Customer customer);
}
