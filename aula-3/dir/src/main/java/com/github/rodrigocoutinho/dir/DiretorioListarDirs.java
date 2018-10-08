package com.github.rodrigocoutinho.dir;

import java.io.File;
import java.util.Arrays;

public class DiretorioListarDirs {



    public static void main(String[] args) {
        File file;
        if (args.length == 0) {
            file = new File(System.getProperty("java.class.path"));
        } else {
            file = new File(args[0]);
        }
        desenhaArvoreDiretorio(file);
    }

    private static void desenhaArvoreDiretorio(File file) {
        File[] files = file.listFiles();

        Arrays.stream(files).filter(file1 -> file1.isDirectory()).forEach(file1 -> {
                System.out.println(file1.getPath());
                 desenhaArvoreDiretorio(file1);
        });


    }
}
