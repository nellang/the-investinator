package theinvestinator.com.dataprocessing.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Exchange_Rate")
public class ExchangeRate {

    @Id
    @Column(name = "exchange_rate_id", nullable = false)
    private Date localDateTime;

    @Column(name = "currency_id", nullable = false)
    private int currencyID;

    @Column(name = "value", nullable = false)
    private double value;

    public ExchangeRate() {

    }

    public ExchangeRate(Date localDateTime, int currencyID, double value) {
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
