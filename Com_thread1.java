public class Sem_thread1 {

    /**
     * @param linhas
     * @param colunas
     * @return
     */
    public static int[][] inicializacaoFixa(int linhas, int colunas) {
        int[][] matriz = new int[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matriz[i][j] = i + j * colunas;
            }
        }
        return matriz;
    }

    public static int[][] inicializacao(int linhas, int colunas, int valor) {
        int[][] matriz = new int[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matriz[i][j] = valor;
            }
        }
        return matriz;
    }

    public static void printMatriz(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int[][] multMatriz(int[][] mA, int[][] mB) {
        int linhasA = mA.length;
        int colunasA = mA[0].length;
        int colunasB = mB[0].length;

        int[][] result = new int[linhasA][colunasB];

        for (int i = 0; i < linhasA; i++) {
            for (int j = 0; j < colunasB; j++) {
                for (int k = 0; k < colunasA; k++) {
                    result[i][j] += mA[i][k] * mB[k][j];
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] mA = inicializacaoFixa(1250, 1000);
        int[][] mB = inicializacaoFixa(1000, 1250);

        for (int i = 0; i < 5; i++) {
            long startTime = System.nanoTime();
            int[][] mC = multMatriz(mA, mB);
            long endTime = System.nanoTime();
            double elapsedTime = (endTime - startTime) / 1e9;
            System.out.printf("Levou %.4f segundos para processar.%n", elapsedTime);
        }
    }

    @Override
    public String toString() {
        return "Sem_thread1 []";
    }
}
