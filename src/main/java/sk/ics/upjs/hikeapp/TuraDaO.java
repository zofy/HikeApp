package sk.ics.upjs.hikeapp;

import java.util.List;

public interface TuraDaO {

    public void pridaj(Tura tura);

    public List<Tura> dajVsetky();

    public List<Tura> dajVybraneTury(String pohorie);

    public void vymaz(Tura tura);

    public List<String> dajZoznamPohori();

    public List<String> dajRocneObdobie();
}
