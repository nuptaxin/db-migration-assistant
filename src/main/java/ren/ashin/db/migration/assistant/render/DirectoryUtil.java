package ren.ashin.db.migration.assistant.render;

import java.io.File;

import org.apache.commons.io.FileUtils;

/**
 * @author renzx
 *
 */
public class DirectoryUtil {
    public static boolean checkDirectory(String dir) {
        File directory = FileUtils.getFile(dir);
        return directory.exists() && directory.isDirectory();
    }

    public static boolean makeDirectory(String dir) {
        File directory = FileUtils.getFile(dir);
        directory.mkdirs();
        return directory.exists() && directory.isDirectory();
    }

}
