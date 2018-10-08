package com.github.rodrigocoutinho.dir;

import java.io.File;
import java.util.Arrays;

public class DiretorioListarArquivos {



    public static void main(String[] args) {
        File file;
        if (args.length == 0) {
            file = new File(System.getProperty("java.class.path"));
        } else {
            file = new File(args[0]);
        }
        listarArquivos(file);
    }

    private static void listarArquivos(File file) {
        File[] files = file.listFiles();

        Arrays.stream(files).forEach(file1 -> {
                if (file1.isDirectory()) {
                    listarArquivos(file1);
                } else {
                    System.out.println(file1.getPath());
                }
        });


    }
}
