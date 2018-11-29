package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Integer> {
    Currency save(Currency currency);

    Currency findById(int currency_id);
}
