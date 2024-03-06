package llamakot.bettermappet.api.scripts.user;

import llamakot.bettermappet.utils.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;

public interface IScriptHudRender {
    void setRotate(double angle, double x, double y, double z);
    ScriptVectorAngle getRotate();

    void setPosition(double x, double y);
    ScriptVector getPosition();

    void setScale(double x, double y);
    ScriptVector getScale();

    void setCanceled(boolean canceled);
    boolean isCanceled();
}
