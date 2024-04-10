package org.example.mysystem;

public class Sponsor {
    private String name;
    private String email;
    private String phone;
    private String registrationFee;

    public Sponsor(String name, String email, String phone, String registrationFee) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.registrationFee = registrationFee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(String registrationFee) {
        this.registrationFee = registrationFee;
    }
}
