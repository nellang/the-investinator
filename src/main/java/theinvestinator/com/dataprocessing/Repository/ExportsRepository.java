package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.Exports;

import java.time.LocalDate;

@Repository
public interface ExportsRepository extends JpaRepository<Exports, Integer> {
    Exports save(Exports exports);

    boolean existsByDateAndCountryID(LocalDate date, int countryID);

}