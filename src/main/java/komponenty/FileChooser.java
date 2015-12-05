package komponenty;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFileChooser;

public class FileChooser extends JFileChooser {

    @Override
    public void approveSelection() {
        File[] files = getSelectedFiles();
        boolean ap = false;
        for (int i = 0; i < files.length; i++) {

            if (Utils.gif.equals(getTypeDescription(files[i]))) {
                ap = true;
            } else if (Utils.jpeg.equals(Utils.getExtension(files[i]))) {
                ap = true;
            } else if (Utils.jpg.equals(Utils.getExtension(files[i]))) {
                ap = true;
            } else if (Utils.png.equals(Utils.getExtension(files[i]))) {
                ap = true;
            } else if (Utils.tif.equals(Utils.getExtension(files[i]))) {
                ap = true;
            } else if (Utils.tiff.equals(Utils.getExtension(files[i]))) {
                ap = true;
            }
        }
        if (ap) {
            super.approveSelection(); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
