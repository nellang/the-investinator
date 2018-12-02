package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.Imports;

import java.time.LocalDate;

@Repository
public interface ImportsRepository extends JpaRepository<Imports, Integer> {
    Imports save(Imports imports);

    boolean existsById(Integer integer);

    boolean existsByDateAndCountryID(LocalDate date, int countryID);

}