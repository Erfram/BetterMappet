package llama.bettermappet.api.scripts.user;

import llama.bettermappet.api.scripts.code.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;

public interface IScriptHandRender {
    /**
     * Rotates the arm (main or off). angle - angle in degrees (positive angle = counterclockwise rotation), and x, y and z - vector around which the rotation is performed.
     *
     * <pre>{@code
     *    function main(c)
     *    {
     *        const hand = c.player.getHand(0); //0 - main, 1 - off
     *
     *        hand.setRotate(-90, 0, 1, 0); // Rotate 90 degrees clockwise around the vertical axis
     *    }
     *  }</pre>
     */
    void setRotate(double angle, double x, double y, double z);

    /**
     * Return rotate arm.
     *
     * <pre>{@code
     *    function main(c)
     *    {
     *        const rotate = c.player.getHand(0).getRotate(); //0 - main, 1 - off
     *
     *        c.send(rotate.angle); // returns angle arm
     *        c.send(rotate.x); // returns x arm
     *        c.send(rotate.y); // returns y arm
     *        c.send(rotate.z); // returns z arm
     *    }
     *  }</pre>
     */
    ScriptVectorAngle getRotate();

    /**
     * Moves the arm through the coordinates
     *
     * <pre>{@code
     *    function main(c)
     *    {
     *        const hand = c.player.getHand(0); //0 - main, 1 - off
     *
     *        hand.setPosition(0.3, 0, 0);
     *    }
     *  }</pre>
     */
    void setPosition(double x, double y, double z);


    /**
     * Moves the arm through the coordinates
     *
     * <pre>{@code
     *    function main(c)
     *    {
     *        const hand = c.player.getHand(0); //0 - main, 1 - off
     *
     *        hand.setPosition(mappet.vector(0.7, 0, 0));
     *    }
     *  }</pre>
     */
    void setPosition(ScriptVector pos);
    /**
     * Return coordinates arm
     *
     * <pre>{@code
     *    function main(c)
     *    {
     *        const hand = c.player.getHand(0); //0 - main, 1 - off
     *        const position = hand.getPosition();
     *
     *        c.send("x: "+position.x+", y:"+position.y+", z:"+position.z)
     *    }
     *  }</pre>
     *  /**
     */
    ScriptVector getPosition();

    /**
     * Returns whether hand canceled is enabled
     *
     * <pre>{@code
     *    function main(c)
     *    {
     *        const hand = c.player.getHand(0); //0 - main, 1 - off
     *
     *        c.send(hand.isCanceled()) // canceled: boolean
     *    }
     *  }</pre>
     */
    boolean isCanceled();
    /**
     * Enable arm canceled
     *
     * <pre>{@code
     *    function main(c)
     *    {
     *        const hand = c.player.getHand(0); //0 - main, 1 - off
     *
     *        hand.setCanceled(true)
     *    }
     *  }</pre>
     */
    void setCanceled(boolean canceled);

    /**
     * Resets the rotation of the hand to its default value.
     */
    void resetRotate();

    /**
     * Resets the position of the hand to its default value.
     */
    void resetPosition();
}
