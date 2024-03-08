package llama.bettermappet.api.scripts.user;

import llama.bettermappet.utils.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;

public interface IScriptCamera {
    ScriptVectorAngle getRotate();
    void setRotate(float angle, float x, float y, float z);

    ScriptVector getScale();
    void setScale(float x, float y, float z);

    void setCanceled(boolean canceled);
    boolean isCanceled();
}
