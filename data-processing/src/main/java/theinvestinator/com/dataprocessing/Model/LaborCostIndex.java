package theinvestinator.com.dataprocessing.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Labor_Cost_Index")
public class LaborCostIndex {

    @Id
    @Column(name = "labor_cost_index_id", nullable = false)
    private int laborCostIndexID;

    @Column(name = "country_id", nullable = false)
    private int countryID;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "value", nullable = false)
    private double value;

    public LaborCostIndex() {

    }

    public LaborCostIndex(int laborCostIndexID, int countryID, Date date, double value) {
        this.laborCostIndexID = laborCostIndexID;
        this.countryID = countryID;
        this.date = date;
        this.value = value;
    }

    public int getLaborCostIndexID() {
        return laborCostIndexID;
    }

    public void setLaborCostIndexID(int laborCostIndexID) {
        this.laborCostIndexID = laborCostIndexID;
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
