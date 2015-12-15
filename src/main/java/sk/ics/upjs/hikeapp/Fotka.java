package sk.ics.upjs.hikeapp;

import java.awt.Image;

public class Fotka {

    private long id;

    private long idT;

    private long idU;

    private String nazov;

    private Image obrazok;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setObrazok(Image obrazok) {
        this.obrazok = obrazok;
    }

    public Image getObrazok() {
        return obrazok;
    }

    public long getIdT() {
        return idT;
    }

    public long getIdU() {
        return idU;
    }

    public String getNazov() {
        return nazov;
    }

    public void setIdT(Long idT) {
        this.idT = idT;
    }

    public void setIdU(Long idU) {
        this.idU = idU;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

}
