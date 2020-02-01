package com.esprit.intro.miniprojet;

public class Cabinet {
    private Long id;
    private String nom;
    private String emplacement;
    private String info;
    private int Tel;
    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Cabinet(Long id, String nom, String emplacement, String info, int tel) {
        this.id = id;
        this.nom = nom;
        this.emplacement = emplacement;
        this.info = info;
        Tel = tel;
    }

    public Cabinet() {
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public String getInfo() {
        return info;
    }

    public int getTel() {
        return Tel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setTel(int tel) {
        Tel = tel;
    }
}
