package com.model;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @Column(name = "login", nullable = false)
    private String login;

    @Column(nullable = false)
    private String passwordHash;

    @OneToOne
    @JoinColumn(name = "pesel", referencedColumnName = "pesel")
    private PersonalData personalData;

    @Column(nullable = false)
    private UserRole role;

    public User(String login, String passwordHash, PersonalData pesel, UserRole role) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.personalData = pesel;
        this.role = role;
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData pesel) {
        this.personalData = pesel;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
