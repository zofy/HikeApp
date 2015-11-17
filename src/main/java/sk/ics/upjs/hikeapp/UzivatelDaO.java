package sk.ics.upjs.hikeapp;

import java.util.List;

public interface UzivatelDaO {

    public void vlozUzivatela(Uzivatel u);

    public Uzivatel dajUzivatela(String meno, String heslo);

    public List<Uzivatel> dajVsetkych();

    public boolean overMeno(String meno);
}
