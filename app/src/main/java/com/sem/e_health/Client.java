package com.sem.e_health;

public class Client {
    private   String name;
    private String lastName;
    private String phone;
    private String age;

    public Client(String name, String lastName, String phone, String age) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
    }

    public Client() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
