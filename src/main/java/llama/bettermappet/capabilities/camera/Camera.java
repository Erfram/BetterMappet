package llama.bettermappet.capabilities.camera;

import llama.bettermappet.api.scripts.code.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class Camera implements ICamera {
    private EntityPlayer player;

    public ScriptVectorAngle rotate = new ScriptVectorAngle(0, 0, 0, 0);
    public ScriptVector scale = new ScriptVector(1, 1, 1);
    public ScriptVector translate = new ScriptVector(0, 0, 0);
    public boolean canceled = false;

    public static Camera get(EntityPlayer player)
    {
        ICamera cameraCapability = player == null ? null : player.getCapability(CameraProvider.CAMERA, null);

        if (cameraCapability instanceof Camera)
        {
            Camera profile = (Camera) cameraCapability;
            profile.player = player;

            return profile;
        }
        return null;
    }

    @Override
    public ScriptVectorAngle getRotate() {
        return new ScriptVectorAngle(this.rotate.angle, this.rotate.x, this.rotate.y, this.rotate.z);
    }

    @Override
    public void setRotate(float angle, float x, float y, float z) {
        this.rotate.angle = angle;
        this.rotate.x = x;
        this.rotate.y = y;
        this.rotate.z = z;
    }

    @Override
    public ScriptVector getScale() {
        return new ScriptVector(this.scale.x, this.scale.y, this.scale.z);
    }

    @Override
    public void setScale(float x, float y, float z) {
        this.scale.x = x;
        this.scale.y = y;
        this.scale.z = z;
    }

    @Override
    public ScriptVector getTranslate() {
        return this.translate;
    }

    @Override
    public void setTranslate(float x, float y, float z) {
        this.translate.x = x;
        this.translate.y = y;
        this.translate.z = z;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();

        NBTTagCompound rotate = new NBTTagCompound();
        rotate.setFloat("angle", (float)this.rotate.angle);
        rotate.setFloat("x", (float)this.rotate.x);
        rotate.setFloat("y", (float)this.rotate.y);
        rotate.setFloat("z", (float)this.rotate.z);

        NBTTagCompound scale = new NBTTagCompound();
        scale.setFloat("x", (float)this.scale.x);
        scale.setFloat("y", (float)this.scale.y);
        scale.setFloat("z", (float)this.scale.z);

        NBTTagCompound translate = new NBTTagCompound();
        translate.setFloat("x", (float)this.translate.x);
        translate.setFloat("y", (float)this.translate.y);
        translate.setFloat("z", (float)this.translate.z);

        tag.setTag("rotate", rotate);
        tag.setTag("scale", scale);
        tag.setTag("translate", translate);

        tag.setBoolean("canceled", this.canceled);

        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        if (tag.hasKey("rotate")) {
            NBTTagCompound rotate = tag.getCompoundTag("rotate");
            this.rotate = new ScriptVectorAngle(rotate.getFloat("angle"), rotate.getFloat("x"), rotate.getFloat("y"), rotate.getFloat("z"));
        }

        if (tag.hasKey("scale")) {
            NBTTagCompound scale = tag.getCompoundTag("scale");
            this.scale = new ScriptVector(scale.getFloat("x"), scale.getFloat("y"), scale.getFloat("z"));
        }

        if (tag.hasKey("translate")) {
            NBTTagCompound translate = tag.getCompoundTag("translate");
            this.translate = new ScriptVector(translate.getFloat("x"), translate.getFloat("y"), translate.getFloat("z"));
        }

        if (tag.hasKey("canceled")) {
            this.canceled = tag.getBoolean("canceled");
        }
    }
}