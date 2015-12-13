package sk.ics.upjs.hikeapp;

import java.awt.Image;
import java.io.File;
import java.util.List;

public interface FotkaDaO {

    public List<Image> dajFotkyDanejTury(Long idT);

    public void pridajFotky(List<File> fotky, Long idT);

    public List<Image> dajFotkyUzivatela(Long idU);
}
