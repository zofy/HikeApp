package sk.ics.upjs.hikeapp;

import java.util.List;

public interface TuraDaO {

    public void pridaj(Tura tura);

    public List<Tura> dajVsetky();

    public void vymaz(Tura tura);
}
