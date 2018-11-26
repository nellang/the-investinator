package theinvestinator.com.dataprocessing.Model;

import java.util.Date;

public class ExchangeRateModel {

    private Date localDateTime;
    private int currencyID;
    private double value;

    public ExchangeRateModel() {

    }

    public ExchangeRateModel(Date localDateTime, int currencyID, double value) {
        this.localDateTime = localDateTime;
        this.currencyID = currencyID;
        this.value = value;
    }

    public Date getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(Date localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(int currencyID) {
        this.currencyID = currencyID;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
