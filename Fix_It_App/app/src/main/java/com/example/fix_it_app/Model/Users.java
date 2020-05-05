package com.example.fix_it_app.Model;

import com.firebase.ui.auth.data.model.User;

public class Users {
    private String name;
    private String surname;
    private String birthDate;
    private String fiscalCode;
    private String email;
    private String phone;
    private String document;

    public Users(String name, String surname, String birthDate, String fiscalCode, String email, String phone, String document) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.fiscalCode = fiscalCode;
        this.email = email;
        this.phone = phone;
        this.document = document;
    }
    public Users(){}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDocument() {
        return document;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
