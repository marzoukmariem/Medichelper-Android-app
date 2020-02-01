package com.esprit.intro.miniprojet;

public class Contact {

    Long idPatient;
    String nompatient;
    String prenompatient;
    String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNompatient() {
        return nompatient;
    }

    public String getPrenompatient() {
        return prenompatient;
    }

    public void setNompatient(String nompatient) {
        this.nompatient = nompatient;
    }

    public void setPrenompatient(String prenompatient) {
        this.prenompatient = prenompatient;
    }

    public Long getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Long idPatient) {
        this.idPatient = idPatient;
    }

    public Contact() {
    }




}
