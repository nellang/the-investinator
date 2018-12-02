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

    @Column(name = "abbreviation", nullable = false, unique = true)
    private String abbr;

    public Country() {
    }

    public Country(int currencyID, String name, String abbr) {
        this.currencyID = currencyID;
        this.name = name;
        this.abbr = abbr;
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

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}
