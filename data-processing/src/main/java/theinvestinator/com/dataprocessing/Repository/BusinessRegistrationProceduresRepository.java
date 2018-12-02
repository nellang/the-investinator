package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.BusinessRegistrationProcedures;

import java.time.LocalDate;

@Repository
public interface BusinessRegistrationProceduresRepository extends JpaRepository<BusinessRegistrationProcedures, Integer> {
    BusinessRegistrationProcedures save(BusinessRegistrationProcedures registrationProcedures);

    boolean existsById(Integer integer);

    boolean existsByDateAndCountryID(LocalDate date, int countryID);

}