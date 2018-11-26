package theinvestinator.com.dataprocessing.Model;

import java.util.Date;

public class CountryModel {

    private int countryID;
    private int currencyID;
    private String name;

    public CountryModel() {

    }

    public CountryModel(int countryID, int currencyID, String name) {
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
