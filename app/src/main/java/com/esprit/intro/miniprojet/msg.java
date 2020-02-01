package com.esprit.intro.miniprojet;

public class msg {
    private Long id;
    private String detailmsg;
    private Long iduser;

    public msg(Long id, String detailmsg, Long iduser) {
        this.id = id;
        this.detailmsg = detailmsg;
        this.iduser = iduser;
    }


    public Long getId() {
        return id;
    }

    public String getDetailmsg() {
        return detailmsg;
    }

    public Long getIduser() {
        return iduser;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDetailmsg(String detailmsg) {
        this.detailmsg = detailmsg;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }
}


