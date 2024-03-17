package llama.bettermappet.api.scripts.code;

import llama.bettermappet.api.scripts.user.IScriptMatrix;

public class ScriptMatrix implements IScriptMatrix {
    private Double[][] data;
    private int rows;
    private int cols;

    public ScriptMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new Double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.set(i, j, 0);
            }
        }
    }

    public ScriptMatrix(Double[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new Double[rows][cols];

        for (int i = 0; i < rows; i++) {
            if (data[i].length != cols) {
                throw new IllegalArgumentException("All strings must have the same length");
            }
            for (int j = 0; j < cols; j++) {
                this.set(i, j, data[i][j]);
            }
        }
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getCols() {
        return this.cols;
    }

    @Override
    public double get(int row, int col) {
        return this.data[row][col];
    }

    @Override
    public void set(int row, int col, double value) {
        this.data[row][col] = value;
    }

    @Override
    public boolean hasInRadius(int x, int y, int r, Object... args) {
        for (int i = x-r; i <= x+r; i++) {
            for (int j = y-r; j <= y+r; j++) {
                for(Object arg : args) if (this.hasDot(i, j) && this.data[i][j] == arg) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ScriptMatrix copy() {
        return new ScriptMatrix(this.data);
    };

    @Override
    public ScriptMatrix smooth() {
        Double[][] matrix = new Double[][]{};
        for (int y = 0; y < this.getRows(); y++) {
            for (int x = 0; x < this.getCols(); x++) {
                for (int ny = -1; ny <= 1; ny++) {
                    for (int nx = -1; nx <= 1; nx++) {
                        if (this.hasDot(x, y) && this.hasDot(x+nx, y+ny)) {
                            matrix[y+ny][x+nx] = (matrix[y][x] + matrix[y+ny][x+nx]) / 2;
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        return new ScriptMatrix(matrix);
    }

    @Override
    public ScriptMatrix transposeMatrix() {
        Double[][] matrix = new Double[][]{};
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                matrix[i][j] = matrix[j][i];
            }
        }

        return new ScriptMatrix(matrix);
    }

    @Override
    public boolean hasDot(int x, int y) {
        return (x < this.getRows() && y < this.getCols() && x >= 0 && y >= 0 && this.data[y] != null && this.data[y][x] != null);
    }
}