package theinvestinator.com.dataprocessing.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Real_GDP")
public class RealGDP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "real_gdp_id", nullable = false)
    private int gdpID;

    @Column(name = "country_id", nullable = false)
    private int countryID;

    @Column(name = "date", nullable = false, unique = true)
    private Date date;

    @Column(name = "value", nullable = false)
    private double value;

    public RealGDP() {

    }

    public RealGDP(int countryID, Date date, double value) {
        this.countryID = countryID;
        this.date = date;
        this.value = value;
    }

    public int getGdpID() {
        return gdpID;
    }

    public void setGdpID(int gdpID) {
        this.gdpID = gdpID;
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
