import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Com_thread2 {

    private static final String[] NOUNS = {"puppy", "car", "rabbit", "girl", "monkey"};
    private static final String[] VERBS = {"runs", "hits", "jumps", "drives", "barfs"};
    private static final String[] ADJ = {"adorable", "clueless", "dirty", "odd", "stupid"};
    private static final String[] ADV = {"crazily.", "dutifully.", "foolishly.", "merrily.", "occasionally."};

    public static void generateBigRandomSentences(String filename, int numeroLinhas, Object newParam) throws IOException {
        Path path = Paths.get(filename);
        Random random = new Random();

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (int i = 0; i < numeroLinhas; i++) {
                List<String> sentence = List.of(
                        NOUNS[random.nextInt(NOUNS.length)],
                        VERBS[random.nextInt(VERBS.length)],
                        ADJ[random.nextInt(ADJ.length)],
                        ADV[random.nextInt(ADV.length)]
                );
                writer.write(String.join(" ", sentence));
                writer.newLine();
            }
        }
    }

    public static void gerarArquivos(int numeroArquivos, int numeroLinhas) throws IOException {
        for (int i = 0; i < numeroArquivos; i++) {
            generateBigRandomSentences(String.format("arquivoTeste%02d.txt", i), numeroLinhas, null);
        }
    }

    public static void replace(String substr, String newSubstr, int linhaInicio, int linhaFim) throws IOException {
        for (int i = linhaInicio; i < linhaFim; i++) {
            String filename = String.format("arquivoTeste%02d.txt", i);
            Path filePath = Paths.get(filename);
            String content = Files.readString(filePath, StandardCharsets.UTF_8);

            // replace the substr by newSubstr
            content = content.replace(substr, newSubstr);

            // write data into the file
            Files.writeString(filePath.resolveSibling(filename + ".alterado.txt"), content, StandardCharsets.UTF_8);
        }
    }

    public static void main(String[] args) {
        int numeroArquivos = 500;
        int numeroLinhas = 10_000;
        int numThreads = 2;
        int separacao = numeroArquivos / numThreads;

        for (int i = 0; i < 5; i++) {
            long startTime = System.currentTimeMillis();

            try {
                gerarArquivos(numeroArquivos, numeroLinhas);

                ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
                for (int j = 0; j < numThreads; j++) {
                    int linhaInicio = j * separacao;
                    int linhaFim = (j + 1) * separacao < numeroArquivos ? (j + 1) * separacao : numeroArquivos;

                    executorService.execute(() -> {
                        try {
                            replace("car", "truck", linhaInicio, linhaFim);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }

                executorService.shutdown();
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            System.out.printf("Levou %.4f segundos para processar.%n", (endTime - startTime) / 1000.0);
        }
    }
}
