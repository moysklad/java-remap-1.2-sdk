package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractAgent;
import com.lognex.api.util.ID;

public class Employee extends AbstractAgent {
    private String uid;
    private String email;
    private String phone;
    private String middleName;
    private String lastName;
    private String firstName;
    private String fullName;
    private String shortFio;
    private Cashier cashier;

    public Employee() {}

    public Employee(ID id) {
        setId(id);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortFio() {
        return shortFio;
    }

    public void setShortFio(String shortFio) {
        this.shortFio = shortFio;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }
    //cashier
    //attributes
}
