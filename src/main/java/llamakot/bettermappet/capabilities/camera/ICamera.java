package llamakot.bettermappet.capabilities.camera;

import llamakot.bettermappet.utils.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import net.minecraft.nbt.NBTTagCompound;

public interface ICamera {
    ScriptVectorAngle getRotate();
    void setRotate(float angle, float x, float y, float z);

    ScriptVector getScale();
    void setScale(float x, float y, float z);

    void setCanceled(boolean canceled);
    boolean isCanceled();

    NBTTagCompound serializeNBT();
    void deserializeNBT(NBTTagCompound tag);
}
