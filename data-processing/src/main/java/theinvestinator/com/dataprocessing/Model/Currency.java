package theinvestinator.com.dataprocessing.Model;

import javax.persistence.*;

@Entity
@Table(name = "Currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id", nullable = false)
    private int currencyID;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public Currency() {

    }

    public Currency(int currencyID, String name) {
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
