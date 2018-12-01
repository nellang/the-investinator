package theinvestinator.com.dataprocessing.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Business_Registration_Procedures")
public class BusinessRegistrationProcedures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_registration_procedures_id", nullable = false)
    private int registrationProceduresID;

    @Column(name = "country_id", nullable = false)
    private int countryID;

    @Column(name = "date", nullable = false, unique = true)
    private Date date;

    @Column(name = "value", nullable = false)
    private double value;

    public BusinessRegistrationProcedures() {

    }

    public BusinessRegistrationProcedures(int countryID, Date date, double value) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
