package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.ExchangeRate;

import java.util.Date;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Date> {
    ExchangeRate save(ExchangeRate exchangeRate);

    boolean existsById(Date localDateTime);
}
