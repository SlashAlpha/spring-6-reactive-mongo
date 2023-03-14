package reactive_mongo.mappers;

import org.mapstruct.Mapper;
import reactive_mongo.domain.Beer;
import reactive_mongo.model.BeerDTO;

@Mapper
public interface BeerMapper {
    Beer beerDTOToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDTO(Beer beer);
}
