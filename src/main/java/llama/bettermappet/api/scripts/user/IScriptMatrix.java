package llama.bettermappet.api.scripts.user;

import llama.bettermappet.api.scripts.code.ScriptMatrix;

/**
 * Interface representing a script matrix.
 * Defines methods for working with matrices, such as getting dimensions, accessing elements,
 * creating a copy, smoothing, transposing and other operations.
 */
public interface IScriptMatrix {
    /**
     * Returns the number of rows in the matrix.
     *
     * @return the number of rows in the matrix
     */
    int getRows();

    /**
     * Returns the number of columns in the matrix.
     *
     * @return the number of columns in the matrix
     */
    int getCols();

    /**
     * Returns the value of the matrix element by the given row and column indices.
     *
     * @param row row index
     * @param col column index
     * @return value of matrix element by given indices
     */
    double get(int row, int col);

    /**
     * Sets the value of the matrix element by the given row and column indices.
     *
     * @param row row index
     * @param col column index
     * @param value new value of the matrix element
     */
    void set(int row, int col, double value);

    /**
     * Checks if the matrix contains elements within a given radius from a specified point.
     *
     * @param x x-coordinate of the circle center
     * @param y y-coordinate of the circle center
     * @param r radius of the circle
     * @param args additional arguments (if required)
     * @return true if the matrix contains elements within the given radius, otherwise false
     */
    boolean hasInRadius(int x, int y, int r, Object... args);

    /**
     * Creates and returns a copy of the current matrix.
     *
     * @return a copy of the current matrix
     */
    ScriptMatrix copy();

    /**
     * Creates and returns a smoothed version of the current matrix.
     *
     * @return a smoothed version of the current matrix
     */
    ScriptMatrix smooth();

    /**
     * Creates and returns a transposed version of the current matrix.
     *
     * @return the transposed version of the current matrix
     */
    ScriptMatrix transposeMatrix();

    boolean hasDot(int x, int y);
}
