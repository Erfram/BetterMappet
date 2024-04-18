package llama.bettermappet.api.scripts.user;

import llama.bettermappet.api.scripts.code.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;

/**
 * Interface representing a scripted vector angle.
 * This interface defines methods for performing various operations on vector angles,
 * including addition, subtraction, multiplication, length calculation, normalization, scalar and vector product, and others.
 */
public interface IScriptVectorAngle {
    String toString();

    /**
     * Convert this vector angle to an array string
     */
    String toArrayString();

    /**
     * Adds the current scripted vector angle to another scripted vector angle.
     *
     * @param other The other script vector angle to be folded.
     * @return The new script vector angle that is the result of the addition.
     */
    IScriptVectorAngle add(ScriptVectorAngle other);

    /**
     * Subtracts another scripted vector angle from the current scripted vector angle.
     *
     * @param other Another script vector angle to subtract.
     * @return The new script vector angle that is the result of the subtraction.
     */
    IScriptVectorAngle subtract(ScriptVectorAngle other);

    /**
     * Multiplies the current script vector angle by a scalar.
     *
     * @param scalar The scalar to multiply the scripted vector angle by.
     * @return The new script vector angle that is the result of multiplying by the scalar.
     */
    IScriptVectorAngle multiply(double scalar);

    /**
     * Calculates the vector product of the current script vector angle with another script vector angle.
     *
     * @param vector The other script vector angle with which to calculate the vector product.
     * @return The new scripted vector angle that is the result of the vector product.
     */
    IScriptVectorAngle multiply(ScriptVectorAngle vector);

    /**
     * Calculates the length of the current script vector angle.
     *
     * @return The length of the scripted vector angle.
     */
    double length();

    /**
     * Normalizes the current script vector angle.
     *
     * @return New normalized script vector angle.
     */
    IScriptVectorAngle normalize();

    /**
     * Calculates the scalar product of the current script vector angle with another script vector angle.
     *
     * @param vector The other script vector angle with which to calculate the scalar product.
     * @return Scalar product of script vector angles.
     */
    double dotProduct(ScriptVectorAngle vector);

    /**
     * Calculates the vector product of the current script vector angle with another script vector angle.
     *
     * @param vector The other script vector angle with which to calculate the vector product.
     * @return The new scripted vector angle that is the result of the vector product.
     */
    IScriptVectorAngle crossProduct(ScriptVectorAngle vector);

    /**
     * Divides the current script vector angle by another script vector angle.
     *
     * @param vector The other script vector angle to divide the current one by.
     * @return The new script vector angle that is the result of the division.
     */
    IScriptVectorAngle divide(ScriptVectorAngle vector);

    /**
     * Creates a copy of the current scripted vector angle.
     *
     * @return A new script vector angle that is a copy of the current one.
     */
    IScriptVectorAngle copy();

    /**
     * Checks if the current script vector angle is equal to another script vector angle.
     *
     * @param vector The other script vector angle to compare the current one to.
     * @return true if the scripted vector angles are equal, otherwise false.
     */
    boolean equals(ScriptVectorAngle vector);

    /**
     * Checks if the current script vector angle is equal to the given angle and vector components.
     *
     * @param angle Value of the angle.
     * @param x Value of the x-component of the vector.
     * @param y Value of the y component of the vector.
     * @param z Value of the z-component of the vector.
     * @return true if the scripted vector angle is equal to the specified components, otherwise false.
     */
    boolean equals(double angle, double x, double y, double z);
}
