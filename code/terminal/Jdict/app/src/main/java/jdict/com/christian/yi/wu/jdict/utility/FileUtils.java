package jdict.com.christian.yi.wu.jdict.utility;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/20.
 */

public class FileUtils {

    public static final String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator; // SD card path

    /**
     * do: create a directory
     *
     * @param dirName directory name
     * @return true if success
     */
    public static boolean createDir(String dirName) {

        if (!dirName.endsWith(File.separator)) {
            return false;
        } else {
            File dir = new File(SDPATH + dirName);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return true;
        }
    }

    /**
     * do: create a file
     *
     * @param fileName file name
     * @return true if success
     */
    public static boolean createFile(String fileName) {

        if (fileName.endsWith(File.separator)) {
            return false;
        } else {
            File file = new File(SDPATH + fileName);
            if (file.exists()) {
                return true;
            } else {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                try {
                    file.createNewFile();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
    }

    /**
     * do:delete a specified file
     *
     * @param fileName file name
     * @return true if success
     */
    public boolean deleteFile(String fileName) {

        if (fileName.endsWith(File.separator)) {
            return false;
        } else {
            File file = new File(SDPATH + fileName);
            if (file.exists()) {
                file.delete();
            }
            return true;
        }
    }

    /**
     * get the handler to a file
     *
     * @param fileName file name
     * @return handler to a file
     */
    public static File getFile(String fileName) {
        return new File(SDPATH + fileName);
    }

    public static boolean writeFile(byte[] content, String fileName) {

        if (fileName.endsWith(File.separator)) {
            return false;
        } else {
            if (!FileUtils.createFile(fileName)) { // do nothing if exists
                return false;
            } else {
                try {
                    File file = FileUtils.getFile(fileName);
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    bos.write(content);
                    bos.flush();
                    bos.close();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
    }
}


