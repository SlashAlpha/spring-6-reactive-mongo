package reactive_mongo.web.fn;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.util.UriComponentsBuilder;
import reactive_mongo.model.BeerDTO;
import reactive_mongo.model.CustomerDTO;
import reactive_mongo.services.CustomerService;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerHandler {

    private final CustomerService customerService;
    private final Validator validator;

    private void validate(CustomerDTO customerDTO){
        Errors errors =new BeanPropertyBindingResult(customerDTO,"customerDTO");
        validator.validate(customerDTO,errors);

        if(errors.hasErrors()){
            throw new ServerWebInputException(errors.toString());
        }
    }


    public Mono<ServerResponse> deleteCustomer(ServerRequest request) {
        return customerService.getCustomerById(request.pathVariable("customerId"))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(customerDTO -> customerService.deleteCustomer(request.pathVariable("customerId")))
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> patchCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerDTO.class)
                .doOnNext(this::validate)
                .flatMap(customerDTO-> customerService.patchCustomer(request.pathVariable("customerId"),customerDTO))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(savedDTO->ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> updateCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerDTO.class)
                .doOnNext(this::validate)
                .flatMap(customerDTO-> customerService.updateCustomer(request.pathVariable("customerId"),customerDTO))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(savedDTO->ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> createCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerDTO.class)
                .doOnNext(this::validate)
                .flatMap(customerService::saveCustomer)
                .flatMap(savedDTO->ServerResponse.created(UriComponentsBuilder
                        .fromPath(CustomerRouterConfig.CUSTOMER_PATH_ID)
                        .build(savedDTO.getId())).build());
    }

    public Mono<ServerResponse> listCustomers(ServerRequest request) {
      return   ServerResponse.ok()
                .body(customerService.listCustomer(), BeerDTO.class);
    }

    public Mono<ServerResponse> getCustomerById(ServerRequest request) {
        return ServerResponse
                .ok()
                .body(customerService.getCustomerById(request.pathVariable("customerId"))
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))),CustomerDTO.class)
                ;
    }
}
