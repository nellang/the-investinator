package theinvestinator.com.dataprocessing.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Exports_Of_Goods_And_Services")
public class Exports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exports_of_goods_and_services_id", nullable = false)
    private int exportsID;

    @Column(name = "country_id", nullable = false)
    private int countryID;

    @Column(name = "date", nullable = false, unique = true)
    private Date date;

    @Column(name = "value", nullable = false)
    private double value;

    public Exports() {

    }

    public Exports(int countryID, Date date, double value) {
        this.countryID = countryID;
        this.date = date;
        this.value = value;
    }

    public int getExportsID() {
        return exportsID;
    }

    public void setExportsID(int exportsID) {
        this.exportsID = exportsID;
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
