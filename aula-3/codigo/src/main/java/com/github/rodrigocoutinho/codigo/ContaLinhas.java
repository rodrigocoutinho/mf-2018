package com.github.rodrigocoutinho.codigo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ContaLinhas {

    private static int count = 0;

    public static void main(String[] args) {
        File file = null;
        String sufix = null;
        if (args.length == 1) {
            file = new File(System.getProperty("java.class.path"));
            sufix = args[0];
        } else if ( args.length == 2){
            file = new File(args[0]);
            sufix = args[1];
        } else  {
            System.err.println("Argumentos Invalidos");
            System.exit(1);
        }
        listarArquivos(file, sufix);
        System.out.println("Total de linhas: " + count);
    }

    private static void listarArquivos(File file, String sufix) {
        File[] files = file.listFiles();
        Arrays.stream(files).forEach(file1 -> {
                if (file1.isDirectory()) {
                    listarArquivos(file1, sufix);
                } else if (file1.isFile() && file1.getPath().endsWith(sufix)) {
                    try {
                        count += (int) Files.lines(Paths.get(file1.getPath()), StandardCharsets.ISO_8859_1).count();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        });
    }
}
