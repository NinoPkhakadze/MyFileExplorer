package com.example.ninopkhakadze.myfileexplorer;

import java.io.File;

/**
 * Created by NinoPkhakadze on 11/23/2016.
 */


public class CurrentWorkingDirectory {

    private File cwd;
    private File[] cwdListFiles;

    public CurrentWorkingDirectory(String path) {
        cwd = new File(path);
        cwdListFiles = cwd.listFiles();
    }

    public String getPath() {
        return Util.getFilePathSuffix(cwd.getPath());
    }

    public int getContentsCount() {
        return Util.contentsCount(cwdListFiles);
    }

    public File getContent(int position) {
        if (0 <= position && position < cwdListFiles.length) {
            return cwdListFiles[position];
        }

        return null;
    }

    public void changeDirectory(int position) {
        if (position == -1) {
            File parentFile = cwd.getParentFile();
            if (parentFile != null) {
                cwd = parentFile;
                cwdListFiles = parentFile.listFiles();
            }
        } else if (0 <= position && position < cwdListFiles.length) {
            File f = cwdListFiles[position];
            if (f.isDirectory()) {
                cwd = f;
                cwdListFiles = f.listFiles();
            }
        }
    }
}

