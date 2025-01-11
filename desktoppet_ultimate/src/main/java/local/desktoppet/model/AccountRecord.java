package local.desktoppet.model;

import java.time.LocalDate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 账目实体类
 * 
 * @author 2313040108
 * @version 2.5
 */

public class AccountRecord {
    private final StringProperty username;
    private final ObjectProperty<LocalDate> date;
    private final DoubleProperty income;
    private final DoubleProperty outcome;

    public AccountRecord() {
        this(null, null, 0.0, 0.0);
    }

    public AccountRecord(String username, LocalDate date, Double income, Double outcome) {
        this.username = new SimpleStringProperty(username);
        this.date = new SimpleObjectProperty<LocalDate>(date);
        this.income = new SimpleDoubleProperty(income);
        this.outcome = new SimpleDoubleProperty(outcome);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public Double getIncome() {
        return income.get();
    }

    public void setIncome(double income) {
        this.income.set(income);
    }

    public DoubleProperty incomeProperty() {
        return income;
    }

    public Double getOutcome() {
        return outcome.get();
    }

    public void setOutcome(double outcome) {
        this.outcome.set(outcome);
    }

    public DoubleProperty outcomeProperty() {
        return outcome;
    }
    
}
