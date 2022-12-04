
class FloydWarshall {
    final static int INF = 9999, nV = 4;

    void floydWarshall(SPandLength graph[][]) {
        SPandLength matrix[][] = new SPandLength[nV][nV];
        int i, j, k;

        for (i = 0; i < nV; i++)
            for (j = 0; j < nV; j++)
                matrix[i][j] = graph[i][j];

        for (k = 0; k < nV; k++) {
            for (i = 0; i < nV; i++) {
                for (j = 0; j < nV; j++) {
                    if (matrix[i][k].SP + matrix[k][j].SP < matrix[i][j].SP) {
                        matrix[i][j].SP = matrix[i][k].SP + matrix[k][j].SP;
                        matrix[i][j].Length = matrix[i][k].Length + matrix[k][j].Length;
                    }
                }
            }
        }
        printMatrix(matrix);
    }

    void printMatrix(SPandLength matrix[][]) {
        for (int i = 0; i < nV; ++i) {
            for (int j = 0; j < nV; ++j) {
                if (matrix[i][j].SP == INF)
                    System.out.print("INF ");
                else {
                    if (matrix[i][j].Length == 2) {
                        System.out.print(matrix[i][j].SP + "  ");
                    } else {
                        System.out.print("!2 ");
                    }
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SPandLength graph[][] = {
                { new SPandLength(0), new SPandLength(3), new SPandLength(INF), new SPandLength(5) },
                { new SPandLength(2), new SPandLength(0), new SPandLength(INF), new SPandLength(4) },
                { new SPandLength(INF), new SPandLength(1), new SPandLength(0), new SPandLength(INF) },
                { new SPandLength(INF), new SPandLength(INF), new SPandLength(2), new SPandLength(0) }
        };
        FloydWarshall a = new FloydWarshall();
        a.floydWarshall(graph);
    }
}

class SPandLength {
    int SP;
    int Length = 1;

    SPandLength(int SP) {
        this.SP = SP;
    }
}