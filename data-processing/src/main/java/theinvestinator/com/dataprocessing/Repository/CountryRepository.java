package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Country save(Country country);

    Country findById(int country_id);
}
