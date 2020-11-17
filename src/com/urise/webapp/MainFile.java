package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File(".\\src\\com\\urise\\webapp");
        System.out.println(dir.isDirectory());
        System.out.println(Arrays.toString(dir.list()));

        try (FileInputStream fis = new FileInputStream(filePath)) {
            int tmp = fis.read();
        }
        catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        recursiveSearchFiles(new File(".\\src"), 0);
    }

    static void recursiveSearchFiles(File rootDir, int lvl) {
        System.out.println("\t".repeat(Math.max(0, lvl)) + rootDir.getName());
        if (rootDir.isDirectory()) {
            File[] children = rootDir.listFiles();
            if (children != null) {
                for (File file : children) {
                    recursiveSearchFiles(file, lvl + 1);
                }
            }
        }
    }
}
