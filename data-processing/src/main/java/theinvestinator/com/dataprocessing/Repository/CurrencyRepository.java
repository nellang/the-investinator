package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.Currency;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Currency save(Currency currency);

    Currency findById(int currency_id);

    List<Currency> findAll();
}
