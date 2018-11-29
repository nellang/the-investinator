package theinvestinator.com.dataprocessing.Model;

import javax.persistence.*;

@Entity
@Table(name = "Country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false)
    private int countryID;

    @Column(name = "currency_id", nullable = false)
    private int currencyID;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Country() {

    }

    public Country(int countryID, int currencyID, String name) {
        this.countryID = countryID;
        this.currencyID = currencyID;
        this.name = name;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public int getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(int currencyID) {
        this.currencyID = currencyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
