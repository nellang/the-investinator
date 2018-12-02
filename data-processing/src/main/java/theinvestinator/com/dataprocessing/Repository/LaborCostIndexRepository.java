package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.LaborCostIndex;

import java.time.LocalDate;

@Repository
public interface LaborCostIndexRepository extends JpaRepository<LaborCostIndex, Integer> {
    LaborCostIndex save(LaborCostIndex laborCostIndex);

    boolean existsById(Integer integer);

    boolean existsByDateAndCountryID(LocalDate date, int countryID);

}