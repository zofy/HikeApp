package sk.ics.upjs.hikeapp;

import com.mysql.jdbc.Blob;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Stack;

public interface TuraDaO {

    public void pridaj(Tura tura);

    public List<Tura> dajVsetky();

    public List<Tura> dajVybraneTury(Stack<String> nazvyAtributov, Stack<String> hodnotyAtributov);

    public void vymaz(Tura tura);

    public List<String> dajZoznamPohori();

    public String dajPopis(long idT);

    public Blob dajDetail(long idT);

    public String dajNazovTury(long idT);

    public List<Image> dajFotky(long idT);
}
