package org.example.mysystem;

public class Runner {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String registrationFee;
    private String bib;

    public Runner(String name, String email, String phone, String address, String registrationFee) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.registrationFee = registrationFee;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRegistrationFee(String registrationFee) {
        this.registrationFee = registrationFee;
    }

    public void setBib(String bib) {
        this.bib = bib;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getRegistrationFee() {
        return registrationFee;
    }

    public String getBib() {
        return bib;
    }
}
