package theinvestinator.com.dataprocessing.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Exchange_Rate")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_rate_id", nullable = false)
    private int exchangeRateID;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "currency_id", nullable = false)
    private int currencyID;

    @Column(name = "value", nullable = false)
    private double value;

    public ExchangeRate() {
    }

    public ExchangeRate(Date date, int currencyID, double value) {
        this.date = date;
        this.currencyID = currencyID;
        this.value = value;
    }

    public int getExchangeRateID() {
        return exchangeRateID;
    }

    public void setExchangeRateID(int exchangeRateID) {
        this.exchangeRateID = exchangeRateID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
