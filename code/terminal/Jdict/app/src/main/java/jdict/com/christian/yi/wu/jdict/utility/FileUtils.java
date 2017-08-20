package jdict.com.christian.yi.wu.jdict.utility;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/20.
 */

public class FileUtils {

    private static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator; // SD card path

    /**
     * do: get SD path
     *
     * @return
     */
    public static String getSDPATH() {

        return SDPATH;
    }

    /**
     * @param dirName
     * @return
     */
    public static File createDir(String dirName) {

        File file = new File(SDPATH + dirName + File.separator);

        file.mkdirs();

        return file;
    }

    /**
     * cdo: create a file
     * @param fileName
     * @return
     */
    public static File createFile(String fileName) {

        File file = new File(SDPATH + fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


}
