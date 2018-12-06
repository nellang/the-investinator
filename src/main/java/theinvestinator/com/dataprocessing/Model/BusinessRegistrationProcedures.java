package theinvestinator.com.dataprocessing.Model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Business_Registration_Procedures")
public class BusinessRegistrationProcedures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_registration_procedures_id", nullable = false)
    private int registrationProceduresID;

    @Column(name = "country_id", nullable = false)
    private int countryID;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "value", nullable = false)
    private double value;

    public BusinessRegistrationProcedures() {
    }

    public BusinessRegistrationProcedures(int countryID, LocalDate date, double value) {
        this.countryID = countryID;
        this.date = date;
        this.value = value;
    }

    public int getRegistrationProceduresID() {
        return registrationProceduresID;
    }

    public void setRegistrationProceduresID(int registrationProceduresID) {
        this.registrationProceduresID = registrationProceduresID;
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
