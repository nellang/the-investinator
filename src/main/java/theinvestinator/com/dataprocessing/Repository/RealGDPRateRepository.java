package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.RealGDPRate;

import java.time.LocalDate;

@Repository
public interface RealGDPRateRepository extends JpaRepository<RealGDPRate, Integer> {
    RealGDPRate save(RealGDPRate gdpRate);

    boolean existsByDateAndCountryID(LocalDate date, int countryID);

}