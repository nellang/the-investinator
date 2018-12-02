package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.RealGDP;

import java.time.LocalDate;

@Repository
public interface RealGDPRepository extends JpaRepository<RealGDP, Integer> {
    RealGDP save(RealGDP gdp);

    boolean existsById(Integer integer);

    boolean existsByDateAndCountryID(LocalDate date, int countryID);

}