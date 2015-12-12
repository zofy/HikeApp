package sk.ics.upjs.hikeapp;

import java.util.List;

public interface UzivatelDaO {

    public void vlozUzivatela(Uzivatel u);

    public Long getUserId(String meno);

    public List<Uzivatel> dajVsetkych();

    public boolean overMeno(String meno);

    public boolean overUzivatela(String meno, String heslo);

}
