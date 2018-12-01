package theinvestinator.com.dataprocessing.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Model.RealGDPRate;

import java.util.Date;

@Repository
public interface RealGDPRateRepository extends JpaRepository<RealGDPRate, Integer> {
    RealGDPRate save(RealGDPRate gdpRate);

    boolean existsById(Integer integer);

    int findByDate(@Param("date") Date date);

}