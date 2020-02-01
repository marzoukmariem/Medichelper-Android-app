package com.esprit.intro.miniprojet;

public class RendezVous {

    Long id;
    String titre;
    String date;
    String approuve;
    Long id_patient;
    String nompatient;
    String prenompatient;
    String detailrdv;
    String img;
    String numtelp;
    String dateann;
    String professp;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getProfessp() {
        return professp;
    }

    public void setProfessp(String professp) {
        this.professp = professp;
    }

    public String getNumtelp() {
        return numtelp;
    }

    public void setNumtelp(String numtelp) {
        this.numtelp = numtelp;
    }

    public String getDateann() {
        return dateann;
    }

    public void setDateann(String dateann) {
        this.dateann = dateann;
    }

    public String getDetailrdv() {
        return detailrdv;
    }

    public void setDetailrdv(String detailrdv) {
        this.detailrdv = detailrdv;
    }

    public String getTitre() {
        return titre;
    }

    public String getDate() {
        return date;
    }

    public Long getId_patient() {
        return id_patient;
    }

    public void setId_patient(Long id_patient) {
        this.id_patient = id_patient;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getApprouve() {
        return approuve;
    }

    public void setApprouve(String approuve) {
        this.approuve = approuve;
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


    public RendezVous() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
