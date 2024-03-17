package llama.bettermappet.api.scripts.user;

import llama.bettermappet.api.scripts.code.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;

public interface IScriptHudRender {

    /**
     * Sets the angle of rotation of the object around the specified axis.
     *
     * @param angle the rotation angle in radians
     * @param x the X component of the rotation axis vector
     * @param y the Y component of the rotation axis vector
     * @param z the Z component of the rotation axis vector
     */
    void setRotate(double angle, double x, double y, double z);

    /**
     * Returns the current angle of rotation of the object and the axis of rotation.
     *
     * @return a ScriptVectorAngle object containing the rotation angle in radians and the rotation axis vector
     */
    ScriptVectorAngle getRotate();

    /**
     * Sets the position of the object in two-dimensional space.
     *
     * @param x the X coordinate of the object position
     * @param y the Y coordinate of the object position
     */
    void setPosition(double x, double y);

    /**
     * Returns the current position of the object.
     *
     * @return a ScriptVector object representing the position of the object
     */
    ScriptVector getPosition();

    /**
     * Sets the scale of the object.
     *
     * @param x the X component of the scale vector
     * @param y the Y component of the scale vector
     */
    void setScale(double x, double y);

    /**
     * Returns the current scale of the object.
     *
     * @return a ScriptVector object representing the scale of the object
     */
    ScriptVector getScale();

    /**
     * Sets whether the rendering operation should be canceled.
     *
     * @param canceled true to cancel the rendering operation, false otherwise
     */
    void setCanceled(boolean canceled);

    /**
     * Returns whether the rendering operation has been canceled.
     *
     * @return true if the rendering operation has been canceled, false otherwise
     */
    boolean isCanceled();

    /**
     * Resets the rotation of the hud to its default value.
     */
    void resetRotate();

    /**
     * Resets the scale of the hud to its default value.
     */
    void resetScale();

    /**
     * Resets the hud of the camera to its default value.
     */
    void resetPosition();
}
