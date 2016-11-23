package com.example.ninopkhakadze.myfileexplorer;

/**
 * Created by NinoPkhakadze on 11/23/2016.
 */

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class Util {

    public static final String DEFAULT = "default";
    public static final String DOC = "doc";
    public static final String FOLDER = "folder";
    public static final String MP3 = "mp3";
    public static final String PNG = "png";
    public static final String TXT = "txt";
    public static final String XLS = "xls";
    public static final String ZIP = "ic_zip";


    private static final Map<String, Integer> typeToIcon;

    static {
        Map<String, Integer> tmpMap = new HashMap<>();

        tmpMap.put(DEFAULT, R.drawable.icon_file);
        tmpMap.put(DOC, R.drawable.ic_doc);
        tmpMap.put(FOLDER, R.drawable.ic_folder);
        tmpMap.put(MP3, R.drawable.ic_mp3);
        tmpMap.put(PNG, R.drawable.ic_png);
        tmpMap.put(TXT, R.drawable.ic_txt);
        tmpMap.put(XLS, R.drawable.ic_xls);
        tmpMap.put(ZIP, R.drawable.ic_zip);

        typeToIcon = Collections.unmodifiableMap(tmpMap);
    }

    public static int getIcon(File file) {
        int ret;

        if (file == null) {
            ret = -1;
        } else if (file.isFile()) {
            String extension = getFileExtension(file.getName());
            if (!typeToIcon.containsKey(extension)) {
                extension = DEFAULT;
            }
            ret = typeToIcon.get(extension);
        } else {
            ret = typeToIcon.get(FOLDER);
        }

        return ret;
    }

    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    private static final int DIR_PATH_MAX_LENGTH = 30;

    public static String getFilePathSuffix(String path) {
        path = new StringBuilder(path).reverse().toString();

        String[] chunks = path.split("/");

        StringBuilder sb = new StringBuilder();

        for (int i = chunks.length - 1; i >= 0; i--) {
            if (sb.length() + chunks[i].length() > DIR_PATH_MAX_LENGTH) {
                break;
            }

            sb.append(chunks[i]);
            sb.append(" > ");
        }

        return chunks.length == 0 ? " > " : sb.reverse().toString();
    }

    public static String getDesc(File file) {
        String ret = "";
        if (file != null) {
            if (file.isFile()) {
                ret += file.length() + " bytes ";
            } else {
                ret += contentsCount(file.listFiles()) + " items ";
            }

            ret += new SimpleDateFormat("dd/MM/yyyy").format(new Date(file.lastModified()));
        }

        return ret;
    }

    public static int contentsCount(File[] files) {
        return files == null ? 0 : files.length;
    }
}
