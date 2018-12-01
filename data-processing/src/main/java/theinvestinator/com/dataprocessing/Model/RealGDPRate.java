package theinvestinator.com.dataprocessing.Model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Real_GDP_Growth_Rate")
@NamedQuery(name = "GDP_RATE.findByDate", query = "SELECT COUNT(r) FROM Real_GDP_Growth_Rate r WHERE r.date =:date")
@Table(name = "Real_GDP_Growth_Rate")
public class RealGDPRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "real_gdp_growth_rate_id", nullable = false)
    private int gdpRateID;

    @Column(name = "country_id", nullable = false)
    private int countryID;

    @Column(name = "date", nullable = false, unique = true)
    private Date date;

    @Column(name = "value", nullable = false)
    private double value;

    public RealGDPRate() {

    }

    public RealGDPRate(int countryID, Date date, double value) {
        this.countryID = countryID;
        this.date = date;
        this.value = value;
    }

    public int getGdpRateID() {
        return gdpRateID;
    }

    public void setGdpRateID(int gdpRateID) {
        this.gdpRateID = gdpRateID;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
