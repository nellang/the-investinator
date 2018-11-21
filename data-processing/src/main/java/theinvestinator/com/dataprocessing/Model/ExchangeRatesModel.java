package theinvestinator.com.dataprocessing.Model;

import java.util.Date;

public class ExchangeRatesModel {

    private Date localDateTime;
    private double TRYValue;

    public ExchangeRatesModel() {

    }

    public ExchangeRatesModel(Date localDateTime, double TRYValue) {
        this.localDateTime = localDateTime;
        this.TRYValue = TRYValue;
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
