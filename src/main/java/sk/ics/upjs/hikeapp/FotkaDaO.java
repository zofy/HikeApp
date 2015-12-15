package sk.ics.upjs.hikeapp;

import java.awt.Image;
import java.io.File;
import java.util.List;

public interface FotkaDaO {

    public List<Image> dajFotkyDanejTury(long idT);

    public void pridajFotky(List<File> fotky, long idT);

    public List<Image> dajFotkyUzivatela(long idU);

    public List<Fotka> dajAtributyFotkyDanejTury(long idT);

    public void vymazFotku(long idT, long idFotky);
}
