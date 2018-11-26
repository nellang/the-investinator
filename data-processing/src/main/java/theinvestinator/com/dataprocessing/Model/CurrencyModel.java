package theinvestinator.com.dataprocessing.Model;

import java.util.Date;

public class CurrencyModel {

    private int currencyID;
    private String name;

    public CurrencyModel() {

    }

    public CurrencyModel(int currencyID, String name) {
        this.currencyID = currencyID;
        this.name = name;
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
