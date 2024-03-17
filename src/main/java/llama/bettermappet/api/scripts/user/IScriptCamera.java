package llama.bettermappet.api.scripts.user;

import llama.bettermappet.api.scripts.code.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;

/**
 * Represents an interface for controlling a script-based camera.
 */
public interface IScriptCamera {
    /**
     * Returns the current rotation angle and axis of the camera.
     *
     * @return a ScriptVectorAngle object containing the rotation angle in radians and the rotation axis vector
     */
    ScriptVectorAngle getRotate();

    /**
     * Sets the rotation of the camera around the specified axis.
     *
     * @param angle the rotation angle in radians
     * @param x the X component of the rotation axis vector
     * @param y the Y component of the rotation axis vector
     * @param z the Z component of the rotation axis vector
     */
    void setRotate(float angle, float x, float y, float z);

    /**
     * Returns the current scale of the camera.
     *
     * @return a ScriptVector object representing the scale of the camera
     */
    ScriptVector getScale();

    /**
     * Sets the scale of the camera.
     *
     * @param x the X component of the scale vector
     * @param y the Y component of the scale vector
     * @param z the Z component of the scale vector
     */
    void setScale(float x, float y, float z);

    /**
     * Returns the current position of the camera.
     *
     * @return a ScriptVector object representing the position of the camera
     */
    ScriptVector getPosition();

    /**
     * Sets the position of the camera.
     *
     * @param x the X component of the position vector
     * @param y the Y component of the position vector
     * @param z the Z component of the position vector
     */
    void setPosition(float x, float y, float z);

    /**
     * Sets whether the camera operation should be canceled.
     *
     * @param canceled true to cancel the camera operation, false otherwise
     */
    void setCanceled(boolean canceled);

    /**
     * Returns whether the camera operation has been canceled.
     *
     * @return true if the camera operation has been canceled, false otherwise
     */
    boolean isCanceled();

    /**
     * Resets the rotation of the camera to its default value.
     */
    void resetRotate();

    /**
     * Resets the scale of the camera to its default value.
     */
    void resetScale();

    /**
     * Resets the position of the camera to its default value.
     */
    void resetPosition();
}
