package sk.ics.upjs.hikeapp;

import com.mysql.jdbc.Blob;

public class Tura {

    private String pohorie;

    private int obtiaznost;

    private double dlzka;

    private double casovaNarocnost;

    private String popis;

    private String nazov;

    private Blob detail;

    private boolean mimoChodnika;

    private String ciel;

    public String getNazov() {
        return nazov;
    }

    public Blob getDetail() {
        return detail;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public void setDetail(Blob detail) {
        this.detail = detail;
    }

    public void setCiel(String ciel) {
        this.ciel = ciel;
    }

    public String getCiel() {
        return ciel;
    }

    public void setMimoChodnika(boolean mimoChodnika) {
        this.mimoChodnika = mimoChodnika;
    }

    public boolean isMimoChodnika() {
        return mimoChodnika;
    }

    @Override
    public String toString() {
        return pohorie + " obtiaznost: " + obtiaznost + " dlzka: " + dlzka + " casovaNarocnost: "
                + casovaNarocnost + " rocneObdobie: " + rocneObdobie + " hodnotenie: " + hodnotenie;
    }

    private String rocneObdobie;

    private double hodnotenie;

    public void setHodnotenie(double hodnotenie) {
        this.hodnotenie = hodnotenie;
    }

    public double getHodnotenie() {
        return hodnotenie;
    }

    public void setPohorie(String pohorie) {
        this.pohorie = pohorie;
    }

    public void setObtiaznost(int obtiaznost) {
        this.obtiaznost = obtiaznost;
    }

    public void setDlzka(double dlzka) {
        this.dlzka = dlzka;
    }

    public void setCasovaNarocnost(double casovaNarocnost) {
        this.casovaNarocnost = casovaNarocnost;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public void setRocneObdobie(String rocneObdobie) {
        this.rocneObdobie = rocneObdobie;
    }

    public String getPohorie() {
        return pohorie;
    }

    public int getObtiaznost() {
        return obtiaznost;
    }

    public double getDlzka() {
        return dlzka;
    }

    public double getCasovaNarocnost() {
        return casovaNarocnost;
    }

    public String getPopis() {
        return popis;
    }

    public String getRocneObdobie() {
        return rocneObdobie;
    }
}
