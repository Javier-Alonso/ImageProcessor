package es.ull.components;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class PNGSaveFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String s = f.getName().toLowerCase();

        return s.endsWith(".png");
    }

    @Override
    public String getDescription() {
        return ".png";
    }
}
