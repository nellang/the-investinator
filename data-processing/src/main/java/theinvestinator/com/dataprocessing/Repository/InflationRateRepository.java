package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.InflationRate;

import java.time.LocalDate;

@Repository
public interface InflationRateRepository extends JpaRepository<InflationRate, Integer> {
    InflationRate save(InflationRate inflationRate);

    boolean existsByDateAndCountryID(LocalDate date, int countryID);

}