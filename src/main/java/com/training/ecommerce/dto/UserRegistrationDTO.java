package com.training.ecommerce.dto;

public class UserRegistrationDTO {
    private String name;
    private String email;
    private String address;
    private String password;

    public UserRegistrationDTO(String email, String address, String password, String name) {
        this.email = email;
        this.address = address;
        this.password = password;
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}