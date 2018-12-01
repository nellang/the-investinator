package theinvestinator.com.dataprocessing.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Imports_Of_Goods_And_Services")
public class Imports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imports_of_goods_and_services_id", nullable = false)
    private int importsID;

    @Column(name = "country_id", nullable = false)
    private int countryID;

    @Column(name = "date", nullable = false, unique = true)
    private Date date;

    @Column(name = "value", nullable = false)
    private double value;

    public Imports() {

    }

    public Imports(int countryID, Date date, double value) {
        this.countryID = countryID;
        this.date = date;
        this.value = value;
    }

    public int getImportsID() {
        return importsID;
    }

    public void setImportsID(int importsID) {
        this.importsID = importsID;
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
