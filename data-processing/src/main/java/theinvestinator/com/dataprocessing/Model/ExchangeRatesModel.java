package theinvestinator.com.dataprocessing.Model;

import java.util.Date;

public class ExchangeRatesModel {

    private double TRYValue;
    private Date localDateTime;

    public ExchangeRatesModel() {

    }

    public ExchangeRatesModel(double TRYValue, Date localDateTime) {
        this.TRYValue = TRYValue;
        this.localDateTime = localDateTime;
    }

    public double getTRYValue() {
        return TRYValue;
    }

    public void setTRYValue(double TRYValue) {
        this.TRYValue = TRYValue;
    }

    public Date getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(Date localDateTime) {
        this.localDateTime = localDateTime;
    }
}
