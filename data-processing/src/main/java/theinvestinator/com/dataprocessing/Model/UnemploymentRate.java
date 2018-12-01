package theinvestinator.com.dataprocessing.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Unemployment_Rate")
public class UnemploymentRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unemployment_rate_id", nullable = false)
    private int unemployment_rate_ID;

    @Column(name = "country_id", nullable = false)
    private int countryID;

    @Column(name = "date", nullable = false, unique = true)
    private Date date;

    @Column(name = "value", nullable = false)
    private double value;

    public UnemploymentRate() {

    }

    public UnemploymentRate(int countryID, Date date, double value) {
        this.countryID = countryID;
        this.date = date;
        this.value = value;
    }

    public int getUnemployment_rate_ID() {
        return unemployment_rate_ID;
    }

    public void setUnemployment_rate_ID(int unemployment_rate_ID) {
        this.unemployment_rate_ID = unemployment_rate_ID;
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
