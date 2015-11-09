package sk.ics.upjs.hikeapp;

import java.util.List;
import java.util.Stack;

public interface TuraDaO {

    public void pridaj(Tura tura);

    public List<Tura> dajVsetky();

    public List<Tura> dajVybraneTury(Stack<String> nazvyAtributov, Stack<String> hodnotyAtributov);

    public void vymaz(Tura tura);

    public List<String> dajZoznamPohori();

    public List<String> dajRocneObdobie();
}
