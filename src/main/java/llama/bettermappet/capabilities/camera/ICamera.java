package llama.bettermappet.capabilities.camera;

import llama.bettermappet.api.scripts.code.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import net.minecraft.nbt.NBTTagCompound;

public interface ICamera {
    ScriptVectorAngle getRotate();
    void setRotate(float angle, float x, float y, float z);

    ScriptVector getScale();
    void setScale(float x, float y, float z);

    ScriptVector getPosition();
    void setPosition(float x, float y, float z);

    void setCanceled(boolean canceled);
    boolean isCanceled();

    NBTTagCompound serializeNBT();
    void deserializeNBT(NBTTagCompound tag);
}
