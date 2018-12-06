package theinvestinator.com.dataprocessing.Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Labor_Cost_Index")
public class LaborCostIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "labor_cost_index_id", nullable = false)
    private int laborCostIndexID;

    @Column(name = "country_id", nullable = false)
    private int countryID;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "value", nullable = false)
    private double value;

    public LaborCostIndex() {
    }

    public LaborCostIndex(int countryID, LocalDate date, double value) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
