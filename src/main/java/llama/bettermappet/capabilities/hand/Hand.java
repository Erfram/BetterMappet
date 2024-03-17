package llama.bettermappet.capabilities.hand;

import llama.bettermappet.api.scripts.code.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class Hand implements IHand {
    private EntityPlayer player;

    public ScriptVectorAngle mainRotate = new ScriptVectorAngle(0, 0, 0, 0);
    public ScriptVector mainPosition = new ScriptVector(0, 0, 0);

    public ScriptVectorAngle offRotate = new ScriptVectorAngle(0, 0, 0, 0);
    public ScriptVector offPosition = new ScriptVector(0, 0, 0);

    private boolean mainCanceled = false;
    private boolean offCanceled = false;

    private int hand;

    public static Hand get(EntityPlayer player) {
        IHand handCapability = player == null ? null : player.getCapability(HandProvider.HAND, null);

        if (handCapability instanceof Hand) {
            Hand profile = (Hand) handCapability;
            profile.player = player;

            return profile;
        }
        return null;
    }


    @Override
    public void setRotate(double angle, double x, double y, double z) {
        if(this.hand == 0) {
            this.mainRotate = new ScriptVectorAngle(angle, x, y, z);
        }

        if(this.hand == 1) {
            this.offRotate = new ScriptVectorAngle(angle, x, y, z);
        }
    }

    @Override
    public ScriptVectorAngle getRotate() {
        return this.hand == 0 ? this.mainRotate : this.offRotate;
    }

    @Override
    public void setPosition(double x, double y, double z) {
        if(this.hand == 0) {
            this.mainPosition = new ScriptVector(x, y, z);
        }

        if(this.hand == 1) {
            this.offPosition = new ScriptVector(x, y, z);
        }
    }

    @Override
    public ScriptVector getPosition() {
        return this.hand == 0 ? this.mainPosition : this.offPosition;
    }

    @Override
    public void setCanceled(boolean canceled) {
        if(this.hand == 0) {
            this.mainCanceled = canceled;
        }

        if(this.hand == 1) {
            this.offCanceled = canceled;
        }
    }

    @Override
    public boolean isCanceled() {
        return this.hand == 0 ? this.mainCanceled : this.offCanceled;
    }

    public void setTypeHand(int hand) {
        this.hand = hand;
    }

    public int getTypeHand() {
        return this.hand;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();

        NBTTagCompound mainRotate = new NBTTagCompound();
        mainRotate.setDouble("angle", this.mainRotate.angle);
        mainRotate.setDouble("x", this.mainRotate.x);
        mainRotate.setDouble("y", this.mainRotate.y);
        mainRotate.setDouble("z", this.mainRotate.z);

        NBTTagCompound offRotate = new NBTTagCompound();
        offRotate.setDouble("angle", this.offRotate.angle);
        offRotate.setDouble("x", this.offRotate.x);
        offRotate.setDouble("y", this.offRotate.y);
        offRotate.setDouble("z", this.offRotate.z);

        NBTTagCompound mainPosition = new NBTTagCompound();
        mainPosition.setDouble("x", this.mainPosition.x);
        mainPosition.setDouble("y", this.mainPosition.y);
        mainPosition.setDouble("z", this.mainPosition.z);

        NBTTagCompound offPosition = new NBTTagCompound();
        offPosition.setDouble("x", this.offPosition.x);
        offPosition.setDouble("y", this.offPosition.y);
        offPosition.setDouble("z", this.offPosition.z);

        tag.setTag("mainRotate", mainRotate);
        tag.setTag("offRotate", offRotate);
        tag.setTag("mainPosition", mainPosition);
        tag.setTag("offPosition", offPosition);

        tag.setBoolean("mainCanceled", this.mainCanceled);
        tag.setBoolean("offCanceled", this.offCanceled);

        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        if (tag.hasKey("mainRotate")) {
            NBTTagCompound mainRotate = tag.getCompoundTag("mainRotate");
            this.mainRotate = new ScriptVectorAngle(mainRotate.getDouble("angle"), mainRotate.getDouble("x"), mainRotate.getDouble("y"), mainRotate.getDouble("z"));
        }

        if (tag.hasKey("offRotate")) {
            NBTTagCompound offRotate = tag.getCompoundTag("offRotate");
            this.offRotate = new ScriptVectorAngle(offRotate.getDouble("angle"), offRotate.getDouble("x"), offRotate.getDouble("y"), offRotate.getDouble("z"));
        }

        if (tag.hasKey("mainPosition")) {
            NBTTagCompound mainPosition = tag.getCompoundTag("mainPosition");
            this.mainPosition = new ScriptVector(mainPosition.getDouble("x"), mainPosition.getDouble("y"), mainPosition.getDouble("z"));
        }

        if (tag.hasKey("offPosition")) {
            NBTTagCompound offPosition = tag.getCompoundTag("offPosition");
            this.offPosition = new ScriptVector(offPosition.getDouble("x"), offPosition.getDouble("y"), offPosition.getDouble("z"));
        }

        if (tag.hasKey("mainCanceled")) {
            this.mainCanceled = tag.getBoolean("mainCanceled");
        }

        if (tag.hasKey("offCanceled")) {
            this.offCanceled = tag.getBoolean("offCanceled");
        }
    }
}
