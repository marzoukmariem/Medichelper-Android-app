package com.esprit.intro.miniprojet;

public class Patient {

    int id_Patient;
    String nom;
    String Prenom;
    String dateDeNaissance;
    int CIN;
    int numero;
    String Adresse;

    public Patient() {
    }

    public Patient(int id_Patient, String nom, String prenom, String dateDeNaissance, int CIN, int numero, String adresse) {
        this.id_Patient = id_Patient;
        this.nom = nom;
        Prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.CIN = CIN;
        this.numero = numero;
        Adresse = adresse;
    }

    public int getId_Patient() {
        return id_Patient;
    }

    public void setId_Patient(int id_Patient) {
        this.id_Patient = id_Patient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public int getCIN() {
        return CIN;
    }

    public void setCIN(int CIN) {
        this.CIN = CIN;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }


}
