package sk.ics.upjs.hikeapp;

import com.mysql.jdbc.Blob;
import java.util.LinkedList;

public class Tura {

    private Long idT;

    private String pohorie;

    private int obtiaznost;

    private double dlzka;

    private double casovaNarocnost;

    private LinkedList<String> popis;

    private String nazov;

    private String detail;

    private boolean mimoChodnika;

    private String ciel;

    public Long getIdT() {
        return idT;
    }

    public void setIdT(Long idT) {
        this.idT = idT;
    }

    public LinkedList<String> getPopis() {
        return popis;
    }

    public void setPopis(LinkedList<String> popis) {
        this.popis = popis;
    }

    public String getNazov() {
        return nazov;
    }

    public String getDetail() {
        return detail;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public void setDetail(String detail) {
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

    public String getRocneObdobie() {
        return rocneObdobie;
    }
}
