package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.UnemploymentRate;

import java.time.LocalDate;

@Repository
public interface UnemploymentRateRepository extends JpaRepository<UnemploymentRate, Integer> {
    UnemploymentRate save(UnemploymentRate unemploymentRate);

    boolean existsByDateAndCountryID(LocalDate date, int countryID);

}