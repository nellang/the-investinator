package theinvestinator.com.dataprocessing.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Inflation_Rate")
public class InflationRate {

    @Id
    @Column(name = "inflation_rate_id", nullable = false)
    private int inflationRateID;

    @Column(name = "country_id", nullable = false)
    private int countryID;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "value", nullable = false)
    private double value;

    public InflationRate() {

    }

    public InflationRate(int inflationRateID, int countryID, Date date, double value) {
        this.inflationRateID = inflationRateID;
        this.countryID = countryID;
        this.date = date;
        this.value = value;
    }

    public int getInflationRateID() {
        return inflationRateID;
    }

    public void setInflationRateID(int inflationRateID) {
        this.inflationRateID = inflationRateID;
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
