import java.util.Random;

class Com_thread1 {

    // Função para criar uma matriz aleatória
    /**
     * @param linhas
     * @param colunas
     * @return
     */
    public static double[][] inicializacao(int linhas, int colunas) {
        final Random random = new Random();
        double[][] matriz = new double[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matriz[i][j] = random.nextDouble();
            }
        }

        return matriz;
    }

    // Função para multiplicar duas matrizes
    public static void multMatriz(double[][] matrizA, double[][] matrizB, int linhaInicio, int linhaFim, double[][] matrizR) {
        for (int i = linhaInicio; i < linhaFim; i++) {
            for (int j = 0; j < matrizB[0].length; j++) {
                double sum = 0.0;
                for (int k = 0; k < matrizA[0].length; k++) {
                    sum += matrizA[i][k] * matrizB[k][j];
                }
                matrizR[i][j] = sum;
            }
        }
    }
    public static void main(String[] args) {
        int linhasA = 1250;
        int colunasA = 1000;
        int linhasB = 1000;
        int colunasB = 1250;

        // Cria matrizes aleatórias
        double[][] matrizA = inicializacao(linhasA, colunasA);
        double[][] matrizB = inicializacao(linhasB, colunasB);

        double[][] matrizR = new double[linhasA][colunasB]; // Matriz para armazenar o resultado

        int numThreads = 5; // Número de threads

        int separacao = linhasA / numThreads; // Cálculo de quanto cada thread terá da matriz

        for (int i = 0; i < 5; i++) {
            long startTime = System.currentTimeMillis();
            Thread[] threads = new Thread[numThreads];
            for (int j = 0; j < numThreads; j++) {
                int linhaInicio = j * separacao;
                int linhaFim = (j + 1) * separacao < linhasA ? (j + 1) * separacao : linhasA;

                threads[j] = new Thread(() -> multMatriz(matrizA, matrizB, linhaInicio, linhaFim, matrizR));
                threads[j].start();
            }

            try {
                for (Thread thread : threads) {
                    thread.join(); // Sincroniza as threads
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();

            System.out.printf("Tempo de execução com %d threads: %.4f segundos%n", numThreads, (endTime - startTime) / 1000.0);
        }
    }
}
