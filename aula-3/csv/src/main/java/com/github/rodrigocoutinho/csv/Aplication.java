package com.github.rodrigocoutinho.csv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Aplication {

    private static URL url;
    private static File file = new File(System.getProperty("java.io.tmpdir")
            + "//CADASTRO%20DAS%20IES_2011.csv");

    static {
        try {
            url = new URL("http://repositorio.dados.gov.br/educacao/CADASTRO%20DAS%20IES_2011.csv");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        verificaURL(args);
        verificaOS();
        downloadCsv();

        ArrayList<String> csv = readFile();
        ArrayList<String> cab = new ArrayList<>(Arrays.asList(csv.get(0).split(";")));
        csv.subList(0, 1).clear();
        ArrayList<String> estados = new ArrayList<>();
        int ind = cab.indexOf("SIGLA");
        for (String s : csv) {
            String var = s.split(";")[ind];
            estados.add(var);
        }
        Map<String, Long> result = agrupandoEstados(estados);
        Map<String, Long> finalMap = ordenandoEstadosDecrescete(result);
        //Imprimindo resultado
        finalMap.forEach((s, aLong) -> System.out.println(s + " = " + aLong));

    }

    private static Map<String, Long> ordenandoEstadosDecrescete(Map<String, Long> result) {
        Map<String, Long> finalMap = new LinkedHashMap<>();
        result.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        return finalMap;
    }

    private static Map<String, Long> agrupandoEstados(ArrayList<String> estados) {
        return estados.stream().collect(
                Collectors.groupingBy(
                        Function.identity(), Collectors.counting()
                )
        );
    }

    private static ArrayList<String> readFile() throws IOException {
        Stream<String> linhas = Files.lines(Paths.get(file.getPath()), StandardCharsets.ISO_8859_1);
        ArrayList<String> csv = linhas.collect(Collectors.toCollection(ArrayList::new));
        csv.subList(0, 10).clear(); // removendo as 11 primeiras linhas
        csv.subList(csv.size() - 2, csv.size()).clear();// removendo as 2 últimas linhas
        return csv;
    }

    private static void downloadCsv() throws IOException {
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

    private static void verificaOS() {
        if (System.getProperty("os.name").equals("Windows")) {
            file = new File("C://temp//CADASTRO%20DAS%20IES_2011.csv");
        }
    }

    private static void verificaURL(String[] args) throws MalformedURLException {
        if (args.length > 0) {
            url = new URL(args[0]);
        }
    }
}
