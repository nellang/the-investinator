package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {
    Country save(Country country);

    Country findById(int country_id);
}
