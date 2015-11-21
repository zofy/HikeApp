package sk.ics.upjs.hikeapp;

public class Uzivatel {

    private Long id;
    private String meno;
    private String heslo;
    private String rola;

    public void setRola(String rola) {
        this.rola = rola;
    }

    public String getRola() {
        return rola;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }

    public String getMeno() {
        return meno;
    }

    public String getHeslo() {
        return heslo;
    }
}
