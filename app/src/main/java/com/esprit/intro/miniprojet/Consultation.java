package com.esprit.intro.miniprojet;

public class Consultation {

    int id_Consultation;
    String titre;
    String date;
    String details;
    int idPatient;

    public Consultation() {
    }

    public Consultation(int id_Consultation, String titre, String date, String details, int idPatient) {
        this.id_Consultation = id_Consultation;
        this.titre = titre;
        this.date = date;
        this.details = details;
        this.idPatient = idPatient;
    }

    public int getId_Consultation() {
        return id_Consultation;
    }

    public void setId_Consultation(int id_Consultation) {
        this.id_Consultation = id_Consultation;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }
}
