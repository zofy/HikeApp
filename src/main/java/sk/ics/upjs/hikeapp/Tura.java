package sk.ics.upjs.hikeapp;

public class Tura {

    private String pohorie;

    private int obtiaznost;

    private double dlzka;

    private double casovaNarocnost;

    private String popis;

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
