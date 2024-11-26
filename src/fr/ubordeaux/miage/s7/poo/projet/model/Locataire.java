package fr.ubordeaux.miage.s7.poo.projet.model;

import java.time.LocalDate;

public class Locataire {
    private String name;
    private String surname;
    private String gender;
    private LocalDate birthDate;
    private String email;

    public Locataire(String name, String surname, String gender, LocalDate birthDate, String email) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }
}
