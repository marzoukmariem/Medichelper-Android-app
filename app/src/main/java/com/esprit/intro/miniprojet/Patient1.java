package com.esprit.intro.miniprojet;

import java.util.Date;


public class Patient1 {
       private Long id;
       private String Login;
       private String Password;
       private String Role;
       private String Nom;
       private String Prenom;
       private  Date DateNaissance;
       private String NumTel;
       private String Adresse;
       private String UrlImage;
       private String Cin;
       private int cabinet;
       private msg m;
       private  String urrldiplome;

    public String getUrrldiplome() {
        return urrldiplome;
    }

    public void setUrrldiplome(String urrldiplome) {
        this.urrldiplome = urrldiplome;
    }

    public Patient1(Long id, String password, String role, Date dateNaissance, String adresse, String urlImage, String cin, int cabinet) {
        this.id = id;
        Password = password;
        Role = role;
        DateNaissance = dateNaissance;
        Adresse = adresse;
        UrlImage = urlImage;
        Cin = cin;
        this.cabinet = cabinet;
    }


    public Patient1(Long id, String login, String password, String role, String nom, String prenom, Date dateNaissance, String numTel, String adresse, String urlImage, String cin, int cabinet, msg m) {
        this.id = id;
        Login = login;
        Password = password;
        Role = role;
        Nom = nom;
        Prenom = prenom;
        DateNaissance = dateNaissance;
        NumTel = numTel;
        Adresse = adresse;
        UrlImage = urlImage;
        Cin = cin;
        this.cabinet = cabinet;
        this.m = m;
    }


    public Patient1() {
    }

    public Patient1(Long id, String login, String password, String role, String nom, String prenom, Date dateNaissance, String numTel, String adresse, String urlImage, String cin, int c) {
        this.id = id;
        Login = login;
        Password = password;
        Role = role;
        Nom = nom;
        Prenom = prenom;
        DateNaissance = dateNaissance;
        NumTel = numTel;
        Adresse = adresse;
        UrlImage = urlImage;
        Cin = cin;
        this.cabinet = cabinet;
    }

    public Patient1(Long id, String login, String password, String role, String nom, String prenom, String numTel, String cin, int cabinet) {
        this.id = id;
        Login = login;
        Password = password;
        Role = role;
        Nom = nom;
        Prenom = prenom;
        NumTel = numTel;
        Cin = cin;
        this.cabinet = cabinet;
    }

    public Patient1(Long id, String login, String password, String role, String nom, String prenom, String cin, int cabinet) {
        this.id = id;
        Login = login;
        Password = password;
        Role = role;
        Nom = nom;
        Prenom = prenom;
        Cin = cin;
        this.cabinet = cabinet;
    }

    public Patient1(Long id, String login, String password, String role, String nom, String prenom, String numTel, String adresse, String urlImage, String cin, int c) {
        this.id = id;
        Login = login;
        Password = password;
        Role = role;
        Nom = nom;
        Prenom = prenom;
        NumTel = numTel;
        Adresse = adresse;
        UrlImage = urlImage;
        Cin = cin;
        this.cabinet = cabinet;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return Login;
    }

    public String getPassword() {
        return Password;
    }

    public String getRole() {
        return Role;
    }

    public String getNom() {
        return Nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public Date getDateNaissance() {
        return DateNaissance;
    }

    public String getNumTel() {
        return NumTel;
    }

    public String getAdresse() {
        return Adresse;
    }

    public String getUrlImage() {
        return UrlImage;
    }

    public String getCin() {
        return Cin;
    }

    public int getC() {
        return cabinet;
    }

    public msg getM() {
        return m;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setRole(String role) {
        Role = role;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public void setDateNaissance(Date dateNaissance) {
        DateNaissance = dateNaissance;
    }

    public void setNumTel(String numTel) {
        NumTel = numTel;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public void setUrlImage(String urlImage) {
        UrlImage = urlImage;
    }

    public void setCin(String cin) {
        Cin = cin;
    }

    public void setC(int cabinet) {
        this.cabinet = cabinet;
    }

    public void setM(msg m) {
        this.m = m;
    }

    public int getCabinet() {
        return cabinet;
    }

    public void setCabinet(int cabinet) {
        this.cabinet = cabinet;
    }

    @Override
    public String toString() {
        return "Patient1{" +
                "Nom='" + Nom + '\'' +
                ", Prenom='" + Prenom + '\'' +
                ", Adresse='" + Adresse + '\'' +
                ", Cin='" + Cin + '\'' +
                '}';
    }
}
