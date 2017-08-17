package mx.edu.uaz.utils;

import java.io.File;

/**
 * Created by jaetmar on 5/31/17.
 */
public class FilesFromFolder {

    public static File[] listFilesForFolder(String path) {
        return new File(path).listFiles();
    }

    public static String getCompleteFileName(String folderPath, String file) {
        for (final File fileEntry : listFilesForFolder(folderPath)) {
            System.out.print(fileEntry.getName() + " " + file);
            if (fileEntry.getName().contains(file)) {
                return fileEntry.getName();
            }
        }
        return null;
    }
}
